package orm

import java.lang.IllegalArgumentException
import java.sql.DriverManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.jvm.javaType

annotation class Table(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class PrimaryKey()

@Target(AnnotationTarget.PROPERTY)
annotation class Enum()

class Repository(url : String, username : String, password : String){
    val connection = DriverManager.getConnection(url, username, password)

    inline fun <reified T> findByPK(id: Int): T?{
        val tableName = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        val field =  T::class.declaredMembers.find { it.annotations.any { it is PrimaryKey }  }?.name ?: throw IllegalArgumentException("Отсутствует первичный ключ")
        val statement = connection.prepareStatement("select * from " + tableName + " where " + field + "=" + id)
        val res = statement.executeQuery()
        if (res.next()){
            var constr = T::class.constructors.elementAt(0)
            return constr.call(*constr.parameters.map {
                when (it.type.javaType.typeName){
                    "java.lang.String" -> res.getString(it.name)
                    "java.time.LocalDateTime" -> {
                        LocalDateTime.parse(res.getString(it.name), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                    }
                    "int" -> res.getInt(it.name)
                    else -> res.getObject(it.name)
                }
            }.toTypedArray())
        }
        return null
    }

    inline fun <reified T : Any> createTable(){
        val tableName = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        val props = T::class.java.kotlin.declaredMemberProperties
        val str = props.map { propertyToSQL(it) }
        connection.createStatement().executeUpdate("create table " + tableName + "( " + str.joinToString(",") + ")")
    }

    fun propertyToSQL(prop : KProperty<*>) : String{
        return prop.name + " " + if ( prop.annotations.any { it is Enum }  ) "text" else convert(prop.returnType.javaType.typeName) + if (prop.annotations.any { it is PrimaryKey }) " primary key" else "" + if (prop.returnType.isMarkedNullable) "" else " not null"
    }

    fun convert(string: String) = when(string){
        "java.time.LocalDateTime" -> "timestamp"
        "java.lang.String" -> "text"
        "int" -> "integer"
        "double" -> "double precision"
        else -> string
    }

    fun insert(any : Any){
        var tableClass : Class<Any>? = any.javaClass
        while (tableClass != null && !tableClass.isAnnotationPresent(Table::class.java)){
            tableClass = tableClass.superclass as Class<Any>
        }
        if (tableClass == null) throw IllegalArgumentException("Аргументом дложен быть объект из таблицы")
        val tableName = tableClass.annotations.find { it is Table }?.let { (it as Table).name }
        val fields =  tableClass.kotlin.declaredMemberProperties.map { it.name }
        val values =  tableClass.kotlin.declaredMemberProperties.map { it.get(any) }
        val statement = connection.prepareStatement("insert into " + tableName + "(" + fields.joinToString(", ") + ") values (" + values.map { "?" }.joinToString(", ") + ")")
        values.forEachIndexed { i, s -> if (s is kotlin.Enum<*>) statement.setObject(i + 1, s.toString()) else statement.setObject(i + 1, s) }
        statement.executeUpdate()
    }

    inline fun <reified T> selectAll() : ConcurrentLinkedDeque<T>{
        val tableName = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        val statement = connection.prepareStatement("select * from " + tableName)
        val res = statement.executeQuery()
        val resArray : ConcurrentLinkedDeque<T> = ConcurrentLinkedDeque()
        while (res.next()){
            val constr = T::class.constructors.last()
            val params = constr.parameters.map {
                if ((it.type.javaType as Class<*>).isEnum)
                    (it.type.javaType as Class<*>).getMethod("valueOf", String::class.java).invoke(null, res.getString(it.name))
                else when (it.type.javaType.typeName){
                    "java.lang.String" -> res.getString(it.name)
                    "java.time.LocalDateTime" -> {
                        LocalDateTime.parse(res.getString(it.name), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                    }
                    "int" -> res.getInt(it.name)
                    else -> res.getObject(it.name)
                }
            }.toTypedArray()
            resArray.add(constr.call(*params))
        }
        return resArray
    }

    inline fun <reified T> deleteAll(){
        val tableName = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        val statement = connection.prepareStatement("delete from " + tableName)
        statement.executeUpdate()
    }

    inline fun <reified T> deleteByPK(id : Int){
        val tableName = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        val field =  T::class.declaredMembers.find { it.annotations.any { it is PrimaryKey }  }?.name ?: throw IllegalArgumentException("Отсутствует первичный ключ")
        val statement = connection.prepareStatement("delete from " + tableName + " where " + field + "=" + id)
        statement.executeUpdate()
    }
}