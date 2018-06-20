import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import java.lang.IllegalArgumentException
import java.sql.DriverManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.cast
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure

annotation class Table(val name: String)

@Table("Personages")
data class Personage(val name: String?, val date: LocalDateTime, @PrimaryKey val id: Int)

@Target(AnnotationTarget.PROPERTY)
annotation class PrimaryKey()

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

    inline fun <reified T> createTable(){
        val tableName = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        T::class.declaredMembers.map { listOf(it.name, it.returnType.isMarkedNullable) }
        val str = T::class.declaredMembers.map { it.name + " " + convert(it.returnType.javaType.typeName) + if (it.annotations.any { it is PrimaryKey }) " primary key" else "" + if (it.returnType.isMarkedNullable) "" else " not null"}
        connection.createStatement().executeUpdate("create table " + tableName + "( " + str.joinToString(",") + ")")
    }

    fun convert(string: String) = when(string){
        "java.time.LocalDateTime" -> "timestamp"
        "java.lang.String" -> "text"
        "int" -> "integer"
        else -> string
    }

    fun insert(any : Any){
        if (!any.javaClass.annotations.any { it is Table }) throw IllegalArgumentException("Аргументом дложен быть объект из таблицы")
        val tableName = any.javaClass.annotations.find { it is Table }?.let { (it as Table).name }
        val fields =  any.javaClass.kotlin.declaredMemberProperties.map { it.name }
        val values =  any.javaClass.kotlin.declaredMemberProperties.map { it.get(any) }
        val statement = connection.prepareStatement("insert into " + tableName + "(" + fields.joinToString(", ") + ") values (" + values.map { "?" }.joinToString(", ") + ")")
        values.forEachIndexed { i, s -> statement.setObject(i + 1, s) }
        statement.executeUpdate()
    }
}

fun main(args: Array<String>) {
    val repository = Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")
//    repository.createTable<Personage>()
//    repository.insert(Personage("qwerty'); DROP TABLE Personages --", LocalDateTime.now(), 1))
    print(repository.findByPK<Personage>(1))
    FastClasspathScanner("").matchClassesWithAnnotation(Table::class.java, { cls ->
        cls.name
        cls.annotatedInterfaces
        cls.annotations.find { it is Table }?.let { (it as Table).name }
        cls.kotlin.declaredMembers.map { listOf(it.name, it.returnType.isMarkedNullable) }
        cls.kotlin.declaredMembers.map { listOf(it.name, it.returnType.javaType.typeName) }
    }).scan()

}

//cls.kotlin.primaryConstructor.parameters.map { it.name }