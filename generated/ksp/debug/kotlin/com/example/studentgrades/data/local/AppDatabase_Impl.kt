package com.example.studentgrades.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _disciplineDao: Lazy<DisciplineDao> = lazy {
    DisciplineDao_Impl(this)
  }

  private val _gradeDao: Lazy<GradeDao> = lazy {
    GradeDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "2838ab5e3a8182fd5b7cc5ad30f932c5", "4beb9465fe2a656b44ec380efcbd7ee2") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `disciplines` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `teacherName` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `grades` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `disciplineId` INTEGER NOT NULL, `value` INTEGER NOT NULL, `date` INTEGER NOT NULL, `workType` TEXT NOT NULL, `comment` TEXT NOT NULL, FOREIGN KEY(`disciplineId`) REFERENCES `disciplines`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_grades_disciplineId` ON `grades` (`disciplineId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2838ab5e3a8182fd5b7cc5ad30f932c5')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `disciplines`")
        connection.execSQL("DROP TABLE IF EXISTS `grades`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsDisciplines: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsDisciplines.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDisciplines.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDisciplines.put("teacherName", TableInfo.Column("teacherName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysDisciplines: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesDisciplines: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoDisciplines: TableInfo = TableInfo("disciplines", _columnsDisciplines, _foreignKeysDisciplines, _indicesDisciplines)
        val _existingDisciplines: TableInfo = read(connection, "disciplines")
        if (!_infoDisciplines.equals(_existingDisciplines)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |disciplines(com.example.studentgrades.data.local.DisciplineEntity).
              | Expected:
              |""".trimMargin() + _infoDisciplines + """
              |
              | Found:
              |""".trimMargin() + _existingDisciplines)
        }
        val _columnsGrades: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsGrades.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGrades.put("disciplineId", TableInfo.Column("disciplineId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGrades.put("value", TableInfo.Column("value", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGrades.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGrades.put("workType", TableInfo.Column("workType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGrades.put("comment", TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysGrades: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysGrades.add(TableInfo.ForeignKey("disciplines", "RESTRICT", "NO ACTION", listOf("disciplineId"), listOf("id")))
        val _indicesGrades: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesGrades.add(TableInfo.Index("index_grades_disciplineId", false, listOf("disciplineId"), listOf("ASC")))
        val _infoGrades: TableInfo = TableInfo("grades", _columnsGrades, _foreignKeysGrades, _indicesGrades)
        val _existingGrades: TableInfo = read(connection, "grades")
        if (!_infoGrades.equals(_existingGrades)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |grades(com.example.studentgrades.data.local.GradeEntity).
              | Expected:
              |""".trimMargin() + _infoGrades + """
              |
              | Found:
              |""".trimMargin() + _existingGrades)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "disciplines", "grades")
  }

  public override fun clearAllTables() {
    super.performClear(true, "grades", "disciplines")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(DisciplineDao::class, DisciplineDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(GradeDao::class, GradeDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun disciplineDao(): DisciplineDao = _disciplineDao.value

  public override fun gradeDao(): GradeDao = _gradeDao.value
}
