package com.example.studentgrades.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
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
public class DisciplineDao_Impl(
  __db: RoomDatabase,
) : DisciplineDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfDisciplineEntity: EntityInsertAdapter<DisciplineEntity>

  private val __deleteAdapterOfDisciplineEntity: EntityDeleteOrUpdateAdapter<DisciplineEntity>

  private val __updateAdapterOfDisciplineEntity: EntityDeleteOrUpdateAdapter<DisciplineEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfDisciplineEntity = object : EntityInsertAdapter<DisciplineEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `disciplines` (`id`,`name`,`teacherName`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: DisciplineEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        val _tmpTeacherName: String? = entity.teacherName
        if (_tmpTeacherName == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpTeacherName)
        }
      }
    }
    this.__deleteAdapterOfDisciplineEntity = object : EntityDeleteOrUpdateAdapter<DisciplineEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `disciplines` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: DisciplineEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfDisciplineEntity = object : EntityDeleteOrUpdateAdapter<DisciplineEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `disciplines` SET `id` = ?,`name` = ?,`teacherName` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: DisciplineEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        val _tmpTeacherName: String? = entity.teacherName
        if (_tmpTeacherName == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpTeacherName)
        }
        statement.bindLong(4, entity.id)
      }
    }
  }

  public override suspend fun insert(discipline: DisciplineEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfDisciplineEntity.insertAndReturnId(_connection, discipline)
    _result
  }

  public override suspend fun delete(discipline: DisciplineEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfDisciplineEntity.handle(_connection, discipline)
  }

  public override suspend fun update(discipline: DisciplineEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfDisciplineEntity.handle(_connection, discipline)
  }

  public override fun observeAll(): Flow<List<DisciplineEntity>> {
    val _sql: String = "SELECT * FROM disciplines ORDER BY id"
    return createFlow(__db, false, arrayOf("disciplines")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTeacherName: Int = getColumnIndexOrThrow(_stmt, "teacherName")
        val _result: MutableList<DisciplineEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: DisciplineEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTeacherName: String?
          if (_stmt.isNull(_columnIndexOfTeacherName)) {
            _tmpTeacherName = null
          } else {
            _tmpTeacherName = _stmt.getText(_columnIndexOfTeacherName)
          }
          _item = DisciplineEntity(_tmpId,_tmpName,_tmpTeacherName)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): DisciplineEntity? {
    val _sql: String = "SELECT * FROM disciplines WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTeacherName: Int = getColumnIndexOrThrow(_stmt, "teacherName")
        val _result: DisciplineEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTeacherName: String?
          if (_stmt.isNull(_columnIndexOfTeacherName)) {
            _tmpTeacherName = null
          } else {
            _tmpTeacherName = _stmt.getText(_columnIndexOfTeacherName)
          }
          _result = DisciplineEntity(_tmpId,_tmpName,_tmpTeacherName)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun count(): Int {
    val _sql: String = "SELECT COUNT(*) FROM disciplines"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
