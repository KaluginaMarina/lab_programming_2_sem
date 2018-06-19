import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.jvm.javaType

annotation class Table(val name: String)

@Table("Персонажи")
class Personage(val name: String?, val date: LocalDateTime, @PrimaryKey val id: Int)

@Target(AnnotationTarget.PROPERTY)
annotation class PrimaryKey()

class Repository(){
    inline fun <reified T> findByPK(id: Int): T?{
        val table = T::class.annotations.find { it is Table }?.let { (it as Table).name } ?: throw IllegalArgumentException("Аргументом должна быть таблица")
        T::class.declaredMembers.find { it.annotations.any { it is PrimaryKey }  }?.name ?: throw IllegalArgumentException("Отсутствует первичный ключ")
        return null
    }
}

fun main(args: Array<String>) {
    FastClasspathScanner("").matchClassesWithAnnotation(Table::class.java, { cls ->
        cls.name
        cls.annotatedInterfaces
        cls.annotations.find { it is Table }?.let { (it as Table).name }
        cls.kotlin.declaredMembers.map { listOf(it.name, it.returnType.isMarkedNullable) }
        cls.kotlin.declaredMembers.map { listOf(it.name, it.returnType.javaType.typeName) }
    }).scan()

}

//cls.kotlin.primaryConstructor.parameters.map { it.name }