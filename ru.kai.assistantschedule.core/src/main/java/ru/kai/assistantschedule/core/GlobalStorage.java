package ru.kai.assistantschedule.core;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.crimson.tree.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import ru.kai.assistantschedule.core.xml.DOMSerializer;

/**
 * Класс для сохранения параметров и настроек приложения в файл
 * 
 * @author Дамир
 * 
 */
public class GlobalStorage {

    /**
     * Поля класса определяют, что будет HashMap и что данный класс будет типа
     * SINGLETONE
     */
	private HashMap<String, Object> fHashMap;
	private static GlobalStorage SINGLETON;
	public static String selectedSchedule, selectedProffsLoad;
	public static String[][] matrix;
	public static Date beginingOfSemestr, endOfSemestr;

    /**
     * Закрытый конструктор, кот создает HashMap при первом вызове в блоке
     * статической инициализации
     */
	private GlobalStorage() {
		fHashMap = new HashMap<String, Object>();
	}

    /**
     * Метод возвращения объектов по ключу
     *
     * @param key
     *            - ключ
     * @return соответствующий объект
     */
	public static Object get(String key) {
		return SINGLETON.fHashMap.get(key);
	}

    /**
     * Возвращает объект по ключу, либо объект по умолчанию, если при таком
     * ключе объект не существуют
     *
     * @param key
     *            - ключ
     * @param deflt
     *            - объект по умолчанию
     * @return объект, если он найден при данном ключе, либо объект по
     *         умолчанию, кот был передан в конструкторе
     */
	public static Object get(String key, Object deflt) {
		Object obj = SINGLETON.fHashMap.get(key);
		if (obj == null)
			return deflt;
		else
			return obj;
	}

    /**
     * Достаёт объекты Integer, либо число по умолчанию, если при таком ключе
     * объект не существуют
     *
     * @param key
     *            - ключ
     * @param deflt
     *            - значение по умолчанию
     * @return Integer
     */
	public static int getInt(String key, int deflt) {
		Object obj = SINGLETON.fHashMap.get(key);
		if (obj == null)
			return deflt;
		else
			return new Integer((String) obj).intValue();
	}

    /**
     * Сохраняет все параметры из HashTab в файл
     *
     * @param file
     *            - куда сохраняем
     * @return true если успешно выполнено
     * @throws IOException
     *             - при неудачной сеиализации в файл
     */
	public static boolean save(File file) throws IOException {
        // Создание нового дерева DOM
		DOMImplementation domImpl = DOMImplementationImpl.getDOMImplementation();
		Document doc = domImpl.createDocument(null, "ScheduleHelper-settings", null);
		Element root = doc.getDocumentElement();
		Element propertiesElement = doc.createElement("properties");
		root.appendChild(propertiesElement);
		Set<String> set = SINGLETON.fHashMap.keySet();
		if (set != null) {
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
                // Создаём элемент
				Element propertyElement = doc.createElement("property");
                // Создаём параметр
				String key = iterator.next().toString();
				propertyElement.setAttribute("key", key);
                // Записываем само значение
				Text nameText = doc.createTextNode(get(key).toString());
                // Добавляем в propertY
				propertyElement.appendChild((Node) nameText);
                // Добавляем в propertIES
				propertiesElement.appendChild(propertyElement);
			}
		}
        // Сериализация DOM дерева в файл
		DOMSerializer serializer = new DOMSerializer();
		serializer.serialize(doc, file);
		return true;
	}

    /**
     * Очищает HashTab
     */
	public static void clear() {
		SINGLETON.fHashMap.clear();
	}

    /**
     * Запись в HashTab(Защищена! Выбрасывает IllegalArgumentException)
     *
     * @param key
     *            - (String) ключ
     * @param data
     *            - (String) значение
     */
	public static void put(String key, Object data) throws IllegalArgumentException {
        // Защита от записи NULL элементов в значение
		if (data == null)
			throw new IllegalArgumentException();
		else
			SINGLETON.fHashMap.put(key, data);
	}

    /**
     * Заполняет HashTab из файла
     *
     * @param file
     *            - файл
     * @return true, если успешно и false в противном случае
     * @throws ParserConfigurationException
     *             - связан с открытием файла
     * @throws IOException
     *             - при парсинге
     * @throws SAXException
     *             - при парсинге
     */
	public static boolean load(File file) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		if (doc == null)
			throw new NullPointerException();
		NodeList propertiesNL = doc.getDocumentElement().getChildNodes();
		if (propertiesNL != null) {
			for (int i = 0; (i < propertiesNL.getLength()); i++) {
				if (propertiesNL.item(i).getNodeName().equals("properties")) {
					NodeList propertyList = propertiesNL.item(i).getChildNodes();
					for (int j = 0; j < propertyList.getLength(); j++) {
						NamedNodeMap attributes = propertyList.item(j).getAttributes();
						if (attributes != null) {
							Node n = attributes.getNamedItem("key");
							NodeList childs = propertyList.item(j).getChildNodes();
							if (childs != null) {
								for (int k = 0; k < childs.getLength(); k++) {
									if (childs.item(k).getNodeType() == Node.TEXT_NODE) {
										put(n.getNodeValue(), childs.item(k).getNodeValue());
									}
								}
							}
						}
					}
				}
			}
			return true;
		} else
			return false;
	}

    /**
     * Удаляет запись по данному значению ключа
     *
     * @param key
     */
	public static void del(String key) {
		SINGLETON.fHashMap.remove(key);
	}

    /**
     * Статический блок инициализации
     */
	static {
		SINGLETON = new GlobalStorage();
	}
}