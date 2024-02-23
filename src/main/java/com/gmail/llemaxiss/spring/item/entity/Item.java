package com.gmail.llemaxiss.spring.item.entity;

import com.gmail.llemaxiss.spring.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "spring_item")
@Where(clause = "delete_ts IS NULL")
/**
 * Если нужно версировать на основе всех данных строки, то
 * нужно прописать над классом данные аннотации:
 *
 * @org.hibernate.annotations.OptimisticLocking(type = org.hibernate.annotations.OptimisticLockType.ALL)
 * @org.hibernate.annotations.DynamicUpdate
 *
 * Хибернейт добавит в опреации update WHERE-условия по всех колонкам
 *
 * Если нуно проверять только по измененным полям, а не всем,
 * то указать в первой аннотации тип:
 *
 * org.hibernate.annotations.OptimisticLockType.DIRTY
 *
 */
public class Item extends CommonEntity {
	/**
	 * Включает версионирование
	 * Можно сделать только метод get
	 * Записью будет заниматься БД
	 *
	 * Поля которые изменились, но при этом не должны
	 * влиять на изменение номера версии,
	 * можно пометить:
	 * @org.hibernate.annotations.OptimisticLock(excluded = true)
	 *
	 * Можно использовать версирование с помощью отметки времени:
	 * (стр 317.)
	 */
	@Version
	@Column(name = "version", nullable = false)
	private Long version;
	
	@Column(name = "name")
	private String name;
	
	public Item() {
	}
	
	public Long getVersion() {
		return version;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
