package com.example.studentgrades.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.studentgrades.domain.model.WorkType
import java.time.LocalDate
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class GradeDao_Impl(
  __db: RoomDatabase,
) : GradeDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfGradeEntity: EntityInsertAdapter<GradeEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfGradeEntity: EntityDeleteOrUpdateAdapter<GradeEntity>

  private val __updateAdapterOfGradeEntity: EntityDeleteOrUpdateAdapter<GradeEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfGradeEntity = object : EntityInsertAdapter<GradeEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `grades` (`id`,`disciplineId`,`value`,`date`,`workType`,`comment`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: GradeEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.disciplineId)
        statement.bindLong(3, entity.value.toLong())
        val _tmp: Long? = __converters.dateToEpochDay(entity.date)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmp)
        }
        val _tmp_1: String = __converters.workTypeToString(entity.workType)
        statement.bindText(5, _tmp_1)
        statement.bindText(6, entity.comment)
      }
    }
    this.__deleteAdapterOfGradeEntity = object : EntityDeleteOrUpdateAdapter<GradeEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `grades` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: GradeEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfGradeEntity = object : EntityDeleteOrUpdateAdapter<GradeEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `grades` SET `id` = ?,`disciplineId` = ?,`value` = ?,`date` = ?,`workType` = ?,`comment` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: GradeEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.disciplineId)
        statement.bindLong(3, entity.value.toLong())
        val _tmp: Long? = __converters.dateToEpochDay(entity.date)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmp)
        }
        val _tmp_1: String = __converters.workTypeToString(entity.workType)
        statement.bindText(5, _tmp_1)
        statement.bindText(6, entity.comment)
        statement.bindLong(7, entity.id)
      }
    }
  }

  public override suspend fun insert(grade: GradeEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfGradeEntity.insertAndReturnId(_connection, grade)
    _result
  }

  public override suspend fun delete(grade: GradeEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfGradeEntity.handle(_connection, grade)
  }

  public override suspend fun update(grade: GradeEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfGradeEntity.handle(_connection, grade)
  }

  public override fun observeAll(): Flow<List<GradeEntity>> {
    val _sql: String = "SELECT * FROM grades ORDER BY date DESC, id DESC"
    return createFlow(__db, false, arrayOf("grades")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDisciplineId: Int = getColumnIndexOrThrow(_stmt, "disciplineId")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfWorkType: Int = getColumnIndexOrThrow(_stmt, "workType")
        val _columnIndexOfComment: Int = getColumnIndexOrThrow(_stmt, "comment")
        val _result: MutableList<GradeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GradeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDisciplineId: Long
          _tmpDisciplineId = _stmt.getLong(_columnIndexOfDisciplineId)
          val _tmpValue: Int
          _tmpValue = _stmt.getLong(_columnIndexOfValue).toInt()
          val _tmpDate: LocalDate
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDate)
          }
          val _tmp_1: LocalDate? = __converters.epochDayToDate(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_1
          }
          val _tmpWorkType: WorkType
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfWorkType)
          _tmpWorkType = __converters.stringToWorkType(_tmp_2)
          val _tmpComment: String
          _tmpComment = _stmt.getText(_columnIndexOfComment)
          _item = GradeEntity(_tmpId,_tmpDisciplineId,_tmpValue,_tmpDate,_tmpWorkType,_tmpComment)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeByDiscipline(disciplineId: Long): Flow<List<GradeEntity>> {
    val _sql: String = "SELECT * FROM grades WHERE disciplineId = ? ORDER BY date DESC, id DESC"
    return createFlow(__db, false, arrayOf("grades")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, disciplineId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDisciplineId: Int = getColumnIndexOrThrow(_stmt, "disciplineId")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfWorkType: Int = getColumnIndexOrThrow(_stmt, "workType")
        val _columnIndexOfComment: Int = getColumnIndexOrThrow(_stmt, "comment")
        val _result: MutableList<GradeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GradeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDisciplineId: Long
          _tmpDisciplineId = _stmt.getLong(_columnIndexOfDisciplineId)
          val _tmpValue: Int
          _tmpValue = _stmt.getLong(_columnIndexOfValue).toInt()
          val _tmpDate: LocalDate
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDate)
          }
          val _tmp_1: LocalDate? = __converters.epochDayToDate(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_1
          }
          val _tmpWorkType: WorkType
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfWorkType)
          _tmpWorkType = __converters.stringToWorkType(_tmp_2)
          val _tmpComment: String
          _tmpComment = _stmt.getText(_columnIndexOfComment)
          _item = GradeEntity(_tmpId,_tmpDisciplineId,_tmpValue,_tmpDate,_tmpWorkType,_tmpComment)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): GradeEntity? {
    val _sql: String = "SELECT * FROM grades WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDisciplineId: Int = getColumnIndexOrThrow(_stmt, "disciplineId")
        val _columnIndexOfValue: Int = getColumnIndexOrThrow(_stmt, "value")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfWorkType: Int = getColumnIndexOrThrow(_stmt, "workType")
        val _columnIndexOfComment: Int = getColumnIndexOrThrow(_stmt, "comment")
        val _result: GradeEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDisciplineId: Long
          _tmpDisciplineId = _stmt.getLong(_columnIndexOfDisciplineId)
          val _tmpValue: Int
          _tmpValue = _stmt.getLong(_columnIndexOfValue).toInt()
          val _tmpDate: LocalDate
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDate)
          }
          val _tmp_1: LocalDate? = __converters.epochDayToDate(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.time.LocalDate', but it was NULL.")
          } else {
            _tmpDate = _tmp_1
          }
          val _tmpWorkType: WorkType
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfWorkType)
          _tmpWorkType = __converters.stringToWorkType(_tmp_2)
          val _tmpComment: String
          _tmpComment = _stmt.getText(_columnIndexOfComment)
          _result = GradeEntity(_tmpId,_tmpDisciplineId,_tmpValue,_tmpDate,_tmpWorkType,_tmpComment)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun countByDiscipline(disciplineId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM grades WHERE disciplineId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, disciplineId)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun count(): Int {
    val _sql: String = "SELECT COUNT(*) FROM grades"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: Long) {
    val _sql: String = "DELETE FROM grades WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
