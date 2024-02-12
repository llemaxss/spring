package com.gmail.llemaxiss.spring.person.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.llemaxiss.spring.address.entity.Address;
import com.gmail.llemaxiss.spring.book.entity.Book;
import com.gmail.llemaxiss.spring.common.entity.CommonEntity;
import com.gmail.llemaxiss.spring.courseScore.enums.Course;
import com.gmail.llemaxiss.spring.library.entity.Library;
import com.gmail.llemaxiss.spring.passport.entity.Passport;
import com.gmail.llemaxiss.spring.person.enums.PersonActiveStatus;
import com.gmail.llemaxiss.spring.personInfo.entity.PersonInfo;
import com.gmail.llemaxiss.spring.placeOfBirth.entity.PlaceOfBirth;
import com.gmail.llemaxiss.spring.some.entity.Some;
import com.gmail.llemaxiss.spring.street.entity.converter.StreetNumberConverter2;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converts;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(
	name = "spring_person",
	indexes = {
		@Index(
			name = "person_passport_id_index",
			columnList = "passport_id"
		),
		@Index(
			name = "person_age_index",
			columnList = "age"
		)
	}
)
@Where(clause = "delete_ts IS NULL")
public class Person extends CommonEntity {
	@Column(name = "name")
	private String name;

	@Column(
		name = "age"
		/**
		 * Вместо аннотации @Check, можно использовать параметр columnDefinition
		 * (НО, тип переменной в БД нужно указывать вручную, тип переменной из класса не будет использован ?!)
		*/
		//, columnDefinition = "integer CHECK (age > 0)"
	)
	/**
	 * Создает кастомное ограничение для поля
	 * (можно также использовать аннотацию над всем классом)
	 * Если требуется указать несколько разных ограничений, то можно использваоть @Checks
	 * и внутри уже в виде массива создавать конкретные ограничения
	 *
	 * Все, что указано в constraints будет обернуто в "CHECK (...)"
	 */
	@Check(
		name = "person_age_greater_then_zero",
		constraints = "age > 0"
	)
	private Integer age;

	@Column(name = "active_status")
	@Enumerated(EnumType.STRING)
	private PersonActiveStatus personActiveStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "passport_id",
		referencedColumnName = "id",
		nullable = false,
		unique = true,
		/**
		 * Изменяет название огрпанчения fkey
		 */
		foreignKey = @ForeignKey(
			name = "person_passport_fkey"
			/**
			 * Можно задать собственное определение ограничения в формате:
			 * FOREIGN KEY ([column]) REFERENCES [table]([column]) ON UPDATE [action]
			 * для параметра foreignKeyDefinition
			*/
		)
	)
	/**
	 * Чтобы сериализовать поле, то:
	 * 1) Пишем тут поле которое ссылается на текущую сущность, и "hibernateLazyInitializer" если у нас LAZY-инициализация
	 * 2) Пишем тут поле которое ссылается на текущую сущность, если у нас EAGER-инициализация
	 * 3) ЛУЧШИЙ вариант - создавать свои DTO и заполнять их перед отдачей с контроллера
	 */
	@JsonIgnoreProperties(value = {"person", "hibernateLazyInitializer"})
	private Passport passport;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "place_of_birth_id",
		referencedColumnName = "id",
		nullable = false
	)
	/**
	 * Чтобы сериализовать поле, то:
	 * 1) Пишем тут поле которое ссылается на текущую сущность, и "hibernateLazyInitializer" если у нас LAZY-инициализация
	 * 2) Пишем тут поле которое ссылается на текущую сущность, если у нас EAGER-инициализация
	 * 3) ЛУЧШИЙ вариант - создавать свои DTO и заполнять их перед отдачей с контроллера
	 */
	@JsonIgnoreProperties(value = {"person", "hibernateLazyInitializer"})
	private PlaceOfBirth placeOfBirth;
	
	/**
	 * Обычная реализация ManyToMany
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	/**
	 * Аннотация ставится только для той сущности, который будет хранить и управлять этими ссылками
	 */
	@JoinTable(
		name = "spring_person_book",
		joinColumns = @JoinColumn(name = "person_id"),
		inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	/**
	 * Чтобы сериализовать поле, то:
	 * 1) Пишем тут поле которое ссылается на текущую сущность, и "hibernateLazyInitializer" если у нас LAZY-инициализация
	 * 2) Пишем тут поле которое ссылается на текущую сущность, если у нас EAGER-инициализация
	 * 3) ЛУЧШИЙ вариант - создавать свои DTO и заполнять их перед отдачей с контроллера
	 */
	@JsonIgnoreProperties(value = {"persons", "hibernateLazyInitializer"})
	private List<Book> books;
	
	/**
	 * Кастомная реализация ManyToMany через связь OneToMany,
	 * где таблица со своими полями и составным ключом
	 */
	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "person"
	)
	/**
	 * Чтобы сериализовать поле, то:
	 * 1) Пишем тут поле которое ссылается на текущую сущность, и "hibernateLazyInitializer" если у нас LAZY-инициализация
	 * 2) Пишем тут поле которое ссылается на текущую сущность, если у нас EAGER-инициализация
	 * 3) ЛУЧШИЙ вариант - создавать свои DTO и заполнять их перед отдачей с контроллера
	 */
	@JsonIgnoreProperties(value = {"person", "magazine", "hibernateLazyInitializer"})
	private List<Library> libraries;
	
	/**
	 * Будет null, если все поля Address = null
 	 */
	/**
	 * и/или указать @Embedded, и/или @Embeddable в Address
	 */
	@Embedded
	/**
	 * @AttributeOverrides нужен, так как ниже есть еще один объект Address
	 * Данная аннотация заменяет все определения изначальной колонки
	 * (включая огранчиения, поэтому их нужно заново указывать в @Column если необходимо)
	 */
	@AttributeOverrides({
		@AttributeOverride(
			name = "country",
			column = @Column(name = "country_1")
		),
		@AttributeOverride(
			name = "city",
			column = @Column(name = "city_1")
		),
		@AttributeOverride(
			name = "street.name",
			column = @Column(name = "street_name_1")
		),
		@AttributeOverride(
			name = "street.number",
			column = @Column(name = "street_number_1")
		)
	})
	/**
	 * используется конвертер StreetNumberConverter1 из Address.street.number
	 */
	private Address address1;

	/**
	 * Будет null, если все поля Address = null
	 */
	/**
	 * и/или указать @Embedded, и/или @Embeddable в Address
	 */
	@Embedded
	/**
	 * @AttributeOverrides нужен, так как выше есть еще один объект Address
	 * Данная аннотация заменяет все определения изначальной колонки
	 * (включая огранчиения, поэтому их нужно заново указывать в @Column если необходимо)
	 */
	@AttributeOverrides({
		@AttributeOverride(
			name = "country",
			column = @Column(name = "country_2")
		),
		@AttributeOverride(
			name = "city",
			column = @Column(name = "city_2")
		),
		@AttributeOverride(
			name = "street.name",
			column = @Column(name = "street_name_2")
		),
		@AttributeOverride(
			name = "street.number",
			column = @Column(name = "street_number_2")
		)
	})
	/**
	 * заменяет конвертер который указан в Address.street.number
	 */
	@Converts({
		@Convert(
			attributeName = "street.number",
			converter = StreetNumberConverter2.class
		)
	})
	private Address address2;

	/**
	 * Поля, не помеченные @Entity должны implements Serializable.
	 * Тогда данное поле будет хранить массив байт данного объекта,
	 * иначе выпадет ошибка при компилции
	 */
	@Column(name = "info")
	private PersonInfo personInfo;

	/**
	 * Создастя новая таблица spring_person_type.
	 * В ней появится колонка person_id с ссылкой на текущую запись person
	 * Так же в ней будет сама колонка person_type
	 *
	 * В Person НИКАКИХ колонок не будет
	 * По сути это отображение oneToMany
	 */
	@ElementCollection
	@CollectionTable(
		name = "spring_person_type",
		joinColumns = @JoinColumn(name = "person_id")
	)
	@Column(name = "person_type")
	private Set<String> personTypes = new HashSet<>();

	/**
	 * Создастя новая таблица spring_person_psevdo_name.
	 * В ней повится колонка person_id с ссылкой на текущую запись person
	 * Так же в ней будет сама колонка psevdo_name
	 *
	 * В Person НИКАКИХ колонок не будет
	 * По сути это отображение oneToMany
	 *
	 * Аннотация @OrderColumn добавит колонку order_,
	 * которая будет указывать порядок элементов при вставке
	 */
	@ElementCollection
	@CollectionTable(
		name = "spring_person_psevdo_name",
		joinColumns = @JoinColumn(name = "person_id")
	)
	@OrderColumn(name = "order_")
	@Column(name = "psevdo_name")
	private List<String> psevdoNames = new ArrayList<>();

	/**
	 * Создается новая таблица spring_person_course_score.
	 * В ней появится колонка person_id с ссылкой на текущую запись person
	 * Так же в ней будет сами колонки со значением course_score и ключом course_score_map_key
	 * (название поля + key)
	 *
	 * Аннотация @Column - отвечает за колонку-значения мапы.
	 * Если нужно использовать любой тип времени в качестве ключа,
	 * то использовать @MapKeyTemporal.
	 * Если нужно использовать не enum в качестве ключа,
	 * то использовать аннотацию @MapKeyColumn (для простых классов).
	 * Если нужно использовать сущность в качестве значения,
	 * то использовать аннотацию @MapKey
	 * Если ключ - это сущность, то https://www.baeldung.com/hibernate-persisting-maps
	 */
	@ElementCollection
	@CollectionTable(
		name = "spring_person_course_score",
		joinColumns = @JoinColumn(name = "person_id")
	)
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "course_score")
	private Map<Course, Integer> courseScoreMap = new HashMap<>();

	@Column(name = "some_")
	private Some some;

	@Formula("""
 	age * 10
	""")
	private Integer someValue;

	@Formula("""
 	(SELECT max(p.age) FROM spring_person p WHERE delete_ts IS NULL)
	""")
	private Integer maxAge;

	@Transient
	private String transientField = "transientField";

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public PersonActiveStatus getPersonActiveStatus() {
		return personActiveStatus;
	}

	public void setPersonActiveStatus(PersonActiveStatus personActiveStatus) {
		this.personActiveStatus = personActiveStatus;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public PlaceOfBirth getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(PlaceOfBirth placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public List<Library> getLibraries() {
		return libraries;
	}
	
	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}
	
	public Address getAddress1() {
		return address1;
	}

	public void setAddress1(Address address1) {
		this.address1 = address1;
	}

	public Address getAddress2() {
		return address2;
	}

	public void setAddress2(Address address2) {
		this.address2 = address2;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public Set<String> getPersonTypes() {
		return personTypes;
	}

	public void setPersonTypes(Set<String> personTypes) {
		this.personTypes = personTypes;
	}

	public List<String> getPsevdoNames() {
		return psevdoNames;
	}

	public void setPsevdoNames(List<String> psevdoNames) {
		this.psevdoNames = psevdoNames;
	}

	public Map<Course, Integer> getCourseScoreMap() {
		return courseScoreMap;
	}

	public void setCourseScoreMap(Map<Course, Integer> courseScoreMap) {
		this.courseScoreMap = courseScoreMap;
	}

	public Some getSome() {
		return some;
	}

	public void setSome(Some some) {
		this.some = some;
	}

	public Integer getSomeValue() {
		return someValue;
	}

	public void setSomeValue(Integer someValue) {
		this.someValue = someValue;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getTransientField() {
		return transientField;
	}

	public void setTransientField(String transientField) {
		this.transientField = transientField;
	}
}
