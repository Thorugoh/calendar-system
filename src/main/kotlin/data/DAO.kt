package org.example.data
import javax.persistence.EntityManager

abstract class DAO<TModel, TEntity>(protected val manager: EntityManager, protected val entityType: Class<TEntity>) {
    abstract fun toEntity(obj: TModel): TEntity

    abstract fun toModel(obj: TEntity): TModel

    open fun getList(): List<TModel> {
        val query = manager.createQuery("FROM ${entityType.simpleName}", entityType)
        return query.resultList.map {
            toModel(it)
        }
    }

    open fun add(obj: TModel) {
        val entity = toEntity(obj)
        manager.transaction.begin()
        manager.persist(entity)
        manager.transaction.commit()
    }

    open fun getById(id: Int): TModel {
        val query = manager.createQuery("FROM ${entityType.simpleName} WHERE id=:id", entityType)
        query.setParameter("id", id)
        val entity = query.singleResult
        return toModel(entity)
    }

    open fun delete(id: Int){
        val query = manager.createQuery("FROM ${entityType.simpleName} WHERE id=:id", entityType)
        query.setParameter("id", id)
        val entity = query.singleResult

        manager.transaction.begin()
        manager.remove(entity)
        manager.transaction.commit()
    }
}