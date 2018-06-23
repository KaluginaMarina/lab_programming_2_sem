package orm

annotation class Table(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class PrimaryKey()

@Target(AnnotationTarget.PROPERTY)
annotation class Enum()
