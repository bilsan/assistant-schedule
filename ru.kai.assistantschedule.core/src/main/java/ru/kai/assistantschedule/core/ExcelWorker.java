package ru.kai.assistantschedule.core;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import ru.kai.assistantschedule.core.calendar.Class;
import ru.kai.assistantschedule.core.calendar.FormOfClass;
import ru.kai.assistantschedule.core.calendar.SemestrBuilder;
import ru.kai.assistantschedule.core.calendar.Time;
import ru.kai.assistantschedule.core.exceptions.SheduleIsNotOpenedException;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelWorker {
	private static Workbook workbookSchedule, workbookLoad;
	private static Sheet sheetOfSchedule, sheetOfLoad;
	private static Range[] range;

    // ****************************//
    // ========================== //
    // ДЕЖУРНЫЕ ФУНКЦИИ!!! //
    // ========================== //
    // ****************************//
    /**
     * Открывает книгу с расписанием Excel 97-03 по пути
     *
     * @param inputName
     *            - путь к файлу
     */
	public static boolean openSchedule(String inputName) {
		try {
			workbookSchedule = Workbook.getWorkbook(new File(inputName));
			return true;
		} catch (BiffException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

    /**
     * Открывает книгу с нагрузкой Excel 97-03 по пути
     *
     * @param inputName
     *            - путь к файлу
     */
	public static boolean openLoad(String inputName) {
		try {
			workbookLoad = Workbook.getWorkbook(new File(inputName));
			return true;
		} catch (BiffException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean isScheduleOpened() {
		return (workbookSchedule != null) ? true : false;
	}

	public static boolean isLoadOpened() {
		return (workbookLoad != null) ? true : false;
	}

    /**
     * Закрывает расписание
     */
	public static void closeSchedule() {
		if (workbookSchedule != null)
			workbookSchedule.close();
	}

    /**
     * Закрывает нагрузку
     */
	public static void closeLoad() {
		if (workbookLoad != null)
			workbookLoad.close();
	}

    /**
     * Получает лист в в расписании.
     *
     * @param index
     *            - номер листа. Нумерация начинается с 0.
     */
	public static void selectSheetInSchedule(int index) {
		if (isScheduleOpened())
			sheetOfSchedule = workbookSchedule.getSheet(index);
	}

    /**
     * Получает лист в в нагрузке.
     *
     * @param index
     *            - номер листа. Нумерация начинается с 0.
     */
	public static void selectSheetInLoad(int index) {
		if (isLoadOpened())
			sheetOfLoad = workbookLoad.getSheet(index);
	}

    /**
     * Не доделана!
     */
	public static void tryToFindMergedCells() {
		range = sheetOfSchedule.getMergedCells();
		for (Range cell : range) {
			System.out.print(cell.getTopLeft().getColumn() + " " + cell.getTopLeft().getRow());
			System.out.println(cell.getBottomRight().getColumn() + " " + cell.getBottomRight().getRow());
		}
	}

    // ****************************//
    // ========================== //
    // ФУНКЦИОНАЛ ПРОГРАММЫ!!! //
    // ========================== //
    // ****************************//
    /**
     * Поиск недостающих преподавателей и формирование двумерной матрицы с
     * данными.
     */
	public static String[][] searchEmptyCellsOfPMI() {
		int countOfEmptyCells = 0, startPos;
		String outputInfo[][];

		selectSheetInSchedule(0);
		int i = 0;

		Pattern patternOfGroupName = Pattern.compile("[0-9][0-9][0-9][0-9]");
		while (true) {
			Matcher matchGroupName = patternOfGroupName.matcher(sheetOfSchedule.getCell(0, i).getContents());
			if (matchGroupName.matches())
				break;
			i++;
		}
		startPos = i;
		while (i < sheetOfSchedule.getRows()) {
			if (sheetOfSchedule.getCell(10, i).getContents().toUpperCase().equals("ПМИ") && sheetOfSchedule.getCell(9, i).getContents().isEmpty()) {
				countOfEmptyCells++;
			}
			i++;
		}
		outputInfo = new String[countOfEmptyCells][5];
		i = startPos;
		countOfEmptyCells = 0;
		while (i < sheetOfSchedule.getRows()) {
			if (sheetOfSchedule.getCell(10, i).getContents().toUpperCase().equals("ПМИ") && sheetOfSchedule.getCell(9, i).getContents().isEmpty()) {
				outputInfo[countOfEmptyCells][0] = "" + (i + 1);
				outputInfo[countOfEmptyCells][1] = splitStr(sheetOfSchedule.getCell(0, i).getContents());
				outputInfo[countOfEmptyCells][2] = splitStr(sheetOfSchedule.getCell(4, i).getContents());
				outputInfo[countOfEmptyCells][3] = splitStr(sheetOfSchedule.getCell(5, i).getContents());
				countOfEmptyCells++;
			}
			i++;
		}
		return outputInfo;
	}

    /**
     * Функция будет открываеть лист с именем "Лист1" и в перспективе работать с
     * его содержимым для анализа корректности расписания
     */
	public static String[][] openGeneralLoad(String[][] matrix, int season, int percentMatch) { // Season== 0 - autumn else if == 1 - spring
		sheetOfLoad = workbookLoad.getSheet("Лист1");
		if (sheetOfLoad == null) {
			MessageBox msgBox = new MessageBox(new Shell());
			msgBox.setMessage("Не найден \\\"Лист1\\\" в нагрузке!\" + \"\\nСледует открыть Excel файл нагрузки и изменить название листа \\\"общей нагрузки\\\" на\\\"Лист1\\\"");
			msgBox.open();
			return matrix;
		}

		if (matrix == null) {
			MessageBox msgBox = new MessageBox(new Shell());
			msgBox.setMessage("Нет данных для поиска. Сначала необходимо сделать проверку №1!");
			msgBox.open();
			return matrix;
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 4; j < sheetOfLoad.getRows(); j++) { //
				String[] groups = null;
				if (compareDiscipline(splitStr(sheetOfLoad.getCell(2, j).getContents()), matrix[i][2], percentMatch)) { // matrix[i][2].equals(splitStr(sheetOfLoad.getCell(2,
					// j).getContents()))
					groups = getGroupNames(splitStr(sheetOfLoad.getCell(5, j).getContents()));
					if (groups == null)
						continue;
					for (int k = 0; k < groups.length; k++) {
						if (matrix[i][1].equals(groups[k])) {
							if (season == 0) {
								if (splitStr(matrix[i][3]).equals("лек")) {
									if (sheetOfLoad.getCell(14, j).getContents() != null) {
										matrix[i][4] = splitStr(sheetOfLoad.getCell(14, j).getContents());
										break;
									} else {
										matrix[i][4] = "Не найдено в нагрузке";
										break;
									}
								} else if (splitStr(matrix[i][3]).equals("пр")) {
									if (sheetOfLoad.getCell(17, j).getContents() != null) {
										matrix[i][4] = splitStr(sheetOfLoad.getCell(17, j).getContents());
										break;
									} else {
										matrix[i][4] = "Не найдено в нагрузке";
										break;
									}
								} else if (splitStr(matrix[i][3]).equals("л.р.")) {
									if (sheetOfLoad.getCell(20, j).getContents() != null) {
										matrix[i][4] = splitStr(sheetOfLoad.getCell(20, j).getContents());
										break;
									} else {
										matrix[i][4] = "Не найдено в нагрузке";
										break;
									}
								}
							} else if (season == 1) {
								if (splitStr(matrix[i][3]).equals("лек")) {
									if (sheetOfLoad.getCell(48, j).getContents() != null) {
										matrix[i][4] = splitStr(sheetOfLoad.getCell(48, j).getContents());
										break;
									} else {
										matrix[i][4] = "Не найдено в нагрузке";
										break;
									}
								} else if (splitStr(matrix[i][3]).equals("пр")) {
									if (sheetOfLoad.getCell(51, j).getContents() != null) {
										matrix[i][4] = splitStr(sheetOfLoad.getCell(51, j).getContents());
										break;
									} else {
										matrix[i][4] = "Не найдено в нагрузке";
										break;
									}
								} else if (splitStr(matrix[i][3]).equals("л.р.")) {
									if (sheetOfLoad.getCell(54, j).getContents() != null) {
										matrix[i][4] = splitStr(sheetOfLoad.getCell(54, j).getContents());
										break;
									} else {
										matrix[i][4] = "Не найдено в нагрузке";
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return matrix;
	}

    /**
     * Фукнция проверяет накладки по аудиториям 7го здания
     *
     * @return строку с совпадениями по аудиториям
     */
    /**
     * @return
     */
	public static String checkAuditoriesBlunders() {
		String errorLog = "";
		SortedSet<String> auditoriesSet = new TreeSet<String>();
		selectSheetInSchedule(0); // Открываем лист с расписанием(обычно это первый лист)

        /** Составляем сортированный список аудиторий */
		for (int i = 1; i < sheetOfSchedule.getRows(); i++) {
			if (!sheetOfSchedule.getCell(7, i).getContents().equals("7"))
				continue;
			String str = splitStr(sheetOfSchedule.getCell(6, i).getContents());

            /** Если аудитория не указана, то мы её не учитываем */
			if (str == null || str.equals(""))
				continue;
            /** Обработка кафедр */
			if (Pattern.matches("[Кк][Аа][Фф]", str) || Pattern.matches("[Кк][Аа].", str) || Pattern.matches("[Кк]..", str)) {
				str = "каф";
				String key_value = "";
				String kaf = splitStr(sheetOfSchedule.getCell(10, i).getContents());
				if (kaf == null || kaf.equals(""))
					continue;
				key_value += str.toLowerCase() + kaf.toUpperCase();
				if (auditoriesSet.contains(key_value))
					continue;
				else
					auditoriesSet.add(key_value);
			}

            /** Обработка ВЦ */
			else if (Pattern.matches("[Вв][Цц].*", str)) {
				continue; // Добавить обработку ВЦ
			}
            /** Обработка читальных залов */
			else if (Pattern.matches("[Чч].*[Зз].*", str)) {
				if (auditoriesSet.contains("ч.з."))
					continue;
				else
					auditoriesSet.add("ч.з.");
			}/** Обработка обычных аудиторий */
			else if(Pattern.matches("[0-9]{1,3}[А-Яа-я]{0,1}", str)) {
				if (!auditoriesSet.contains(str))
					auditoriesSet.add(str);
			}
		}
		Iterator<String> it = auditoriesSet.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		it = auditoriesSet.iterator();
		while (it.hasNext()) {
			Week oddW = new Week(false);
			Week evenW = new Week(true);
			String regex = it.next();
			if (Pattern.matches(regex, "ч.з."))
				regex = "[Чч].*[Зз].*";
			for (int i = 1; i < sheetOfSchedule.getRows(); i++) {
				if (!sheetOfSchedule.getCell(7, i).getContents().equals("7"))
					continue;
				String audit = sheetOfSchedule.getCell(6, i).getContents();
				if (audit == null)
					continue;
				if (!Pattern.matches(regex, audit))
					continue;
				String str = splitStr(sheetOfSchedule.getCell(3, i).getContents());
				if (str != null) {
					str = str.replace(" ", ""); // убираем все пробелы
					// str = str.replace("..", "."); //Специфичная ошибка...
				}
				if (str == null || str.equals("") || Pattern.matches("[Чч]../[Нн]..", str) || Pattern.matches("[Нн]../[Чч]..", str)) {
					String key = splitStr(sheetOfSchedule.getCell(1, i).getContents()) + splitStr(sheetOfSchedule.getCell(2, i).getContents());
					if (key != null) {
						if (!evenW.containsKey(key) && !oddW.containsKey(key)) {
							evenW.addLesson(key, splitStr(sheetOfSchedule.getCell(0, i).getContents()) + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
							oddW.addLesson(key, splitStr(sheetOfSchedule.getCell(0, i).getContents()) + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
						} else {
							String[] infoStr = evenW.getValue(key).split(" Дисциплина: ");
                            /**
                             * Если дисциплины совпадают, то записываем как
                             * поток в ту же ячейку, а если различаются, то
                             * записываем в лог
                             */
							if (infoStr.length == 2 && compareDiscipline(infoStr[1], splitStr(sheetOfSchedule.getCell(4, i).getContents()), 100)) {
								evenW.addLesson(key, infoStr[0] + ", " + splitStr(sheetOfSchedule.getCell(0, i).getContents()) + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
								oddW.addLesson(key, infoStr[0] + ", " + splitStr(sheetOfSchedule.getCell(0, i).getContents()) + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
							} else if (infoStr.length == 2 && !compareDiscipline(infoStr[1], splitStr(sheetOfSchedule.getCell(4, i).getContents()), 100))
								errorLog += ((errorLog.length() == 0 ? "" : "\n") + "Еженедельная накладка! Аудитория: " + regex + " Время: " + key + " Группы: " + infoStr[0] + " и " + splitStr(sheetOfSchedule.getCell(0, i).getContents()));
						}
					}
				} else if (str != null && Pattern.matches("[0-9]{1,2}[.][0-9]{1,2}[,][0-9]{1,2}[.][0-9]{1,2}[,][0-9]{1,2}[.][0-9]{1,2}[,][0-9]{1,2}[.][0-9]{1,2}.*", str)) {
					; // не зайдет сюда, т.к. все такие пары назначаются
                    // кафедрам, кот прога не просматривает
				}
			}
			for (int i = 1; i < sheetOfSchedule.getRows(); i++) {
				if (!sheetOfSchedule.getCell(7, i).getContents().equals("7"))
					continue;
				String audit = sheetOfSchedule.getCell(6, i).getContents();
				if (audit == null)
					continue;
				if (!Pattern.matches(regex, audit))
					continue;
				String str = splitStr(sheetOfSchedule.getCell(3, i).getContents());
				if (str != null && (Pattern.matches("[Чч][ЕеЁё][Тт]", str) || Pattern.matches("[Чч][ЕеЁё].", str) || Pattern.matches("[Чч]..", str))) {
					String key = splitStr(sheetOfSchedule.getCell(1, i).getContents()) + splitStr(sheetOfSchedule.getCell(2, i).getContents());
					if (key != null)
						if (!evenW.containsKey(key))
							evenW.addLesson(key, splitStr(sheetOfSchedule.getCell(0, i).getContents()) + "(чет)" + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
						else {
							String[] infoStr = evenW.getValue(key).split(" Дисциплина: ");
                            /**
                             * Если дисциплины совпадают, то записываем как
                             * поток в ту же ячейку, а если различаются, то
                             * записываем в лог
                             */
							if (infoStr.length == 2 && compareDiscipline(infoStr[1], splitStr(sheetOfSchedule.getCell(4, i).getContents()), 100))
								evenW.addLesson(key, infoStr[0] + ", " + splitStr(sheetOfSchedule.getCell(0, i).getContents()) + "(чет)" + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
							else if (infoStr.length == 2 && !compareDiscipline(infoStr[1], splitStr(sheetOfSchedule.getCell(4, i).getContents()), 100))
								errorLog += ((errorLog.length() == 0 ? "" : "\n") + "Чётная накладка! Аудитория: " + regex + " Время: " + key + " Группы: " + infoStr[0] + " и " + splitStr(sheetOfSchedule.getCell(0, i).getContents()));
						}
				} else if (str != null && (Pattern.matches("[Нн][Ее][Чч]", str) || Pattern.matches("[Нн][Ее].", str) || Pattern.matches("[Нн]..", str))) {
					String key = splitStr(sheetOfSchedule.getCell(1, i).getContents()) + splitStr(sheetOfSchedule.getCell(2, i).getContents());
					if (key != null)
						if (!oddW.containsKey(key))
							oddW.addLesson(key, splitStr(sheetOfSchedule.getCell(0, i).getContents()) + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
						else {
							String[] infoStr = oddW.getValue(key).split(" Дисциплина: ");

                            /**
                             * Если дисциплины совпадают, то записываем как
                             * поток в ту же ячейку, а если различаются, то
                             * записываем в лог
                             */
							if (infoStr.length == 2 && compareDiscipline(infoStr[1], splitStr(sheetOfSchedule.getCell(4, i).getContents()), 100))
								oddW.addLesson(key, infoStr[0] + ", " + splitStr(sheetOfSchedule.getCell(0, i).getContents()) + "(неч)" + " Дисциплина: " + splitStr(sheetOfSchedule.getCell(4, i).getContents()));
							else if (infoStr.length == 2 && !compareDiscipline(infoStr[1], splitStr(sheetOfSchedule.getCell(4, i).getContents()), 100))
								errorLog += ((errorLog.length() == 0 ? "" : "\n") + "Нечётная накладка! Аудитория: " + regex + " Время: " + key + " Группы: " + infoStr[0] + " и " + splitStr(sheetOfSchedule.getCell(0, i).getContents()));
						}
				}
			}
		}
		return errorLog;
	}

	public static void GenerateSchedule(SemestrBuilder SB) throws SheduleIsNotOpenedException {
		if(!isScheduleOpened())		//Если не открыто расписание
			throw new SheduleIsNotOpenedException();
		selectSheetInSchedule(0); //Открываем лист с расписанием(обычно это первый лист)
		String errorLog = "";
		int added = 0;
		int doubleAdd = 0;
		for (int i = 1; i < sheetOfSchedule.getRows(); i++) {
			if (!sheetOfSchedule.getCell(7, i).getContents().equals("7"))
				continue;
            /** Если аудитория не указана, то мы её не учитываем */
			String str = splitStr(sheetOfSchedule.getCell(6, i).getContents());
			if (str == null || str.equals(""))
				continue;
			else if (Pattern.matches("[Кк][Аа][Фф]", str) || Pattern.matches("[Кк][Аа].", str) || Pattern.matches("[Кк]..", str)) {
				continue;	//Кафедры тоже пропускаем
			} else if (Pattern.matches("[Вв][Цц].*", str)) {
				continue;	//Игнор ВЦ
			} else {
				Cell[] currentEntry =  sheetOfSchedule.getRow(i);
				int day = convetToDayOfWeek(splitStr(currentEntry[1].getContents()));
				Time time = convertToEnumTime(splitStr(currentEntry[2].getContents()));
				FormOfClass FoC = convertToEnumFormOfClass(splitStr(currentEntry[5].getContents()));
				String tmp = splitStr(currentEntry[3].getContents()).toLowerCase();
				if(tmp.equals("") || Pattern.matches("чет/неч", tmp) || Pattern.matches("неч/чет", tmp) || Pattern.matches("ч.*/н.*", tmp) || Pattern.matches("н.*/ч.*", tmp)){
					for(int j = 0; j < SB.semestr.size(); j++){//Бежим по неделям
						for(int k=0; k < SB.semestr.get(j).days.size(); k++){//Бежим по дням
							if(SB.semestr.get(j).days.get(k).DayOfWeek == day){//Находим нужный день
                                //проверяем накладки
								List<Class> classes = SB.semestr.get(j).days.get(k).classes;
								if(classes.size() == 0){
									classes.add(new Class(time, splitStr(currentEntry[6].getContents()), splitStr(currentEntry[4].getContents()), FoC, splitStr(currentEntry[0].getContents()), splitStr(currentEntry[9].getContents()),splitStr(currentEntry[10].getContents())));
									added++;
									break;
								}
								int classesSize = classes.size();
								int l;
								for(l = 0; l < classesSize; l++){
									if(time == classes.get(l).time){//Время совпало???
										if(splitStr(currentEntry[6].getContents()).equals(classes.get(l).classRoom)){//Аудитория совпала???
											if(splitStr(currentEntry[4].getContents()).equals(classes.get(l).discipline)){//Дисциплина совпала???
												if(FoC == classes.get(l).formOfClass){//Форма занятия совпала?
													if(splitStr(currentEntry[9].getContents()).equals(classes.get(l).prepodavatel)){//Преподаватель совпал???
														classes.get(l).groups += ","+splitStr(currentEntry[0].getContents());
														doubleAdd++;
														break;
													} else
														errorLog += "Два преподавателя в одной аудитории\\n";
												} else
													errorLog += "Не совпала форма занятий\\n";
											} else
												errorLog += "Не совпала дисциплина\\n";
										} else {//Аудитория не совпала, добавляем
											continue;
										}
									} else {//Время не совпало
										continue;
									}
								}
								if(l == classes.size() && errorLog.isEmpty()){//Здесь ошибки быть не может и спокойно добавляем
									classes.add(new Class(time, splitStr(currentEntry[6].getContents()), splitStr(currentEntry[4].getContents()), FoC, splitStr(currentEntry[0].getContents()), splitStr(currentEntry[9].getContents()),splitStr(currentEntry[10].getContents())));
									added++;
								} else if(!errorLog.isEmpty()){//Если ошибка все таки есть то выводим её и не добавляем!!!
									System.out.print(errorLog);
									errorLog = "";
								}
							}
						}
					}
				} else if(Pattern.matches("чет", tmp) || Pattern.matches("че.*", tmp) || Pattern.matches("ч.*", tmp)){
				} else if(Pattern.matches("неч", tmp) || Pattern.matches("не.*", tmp) || Pattern.matches("н.*", tmp)){
				} else if(Pattern.matches("до.*", tmp)){
				} else if(Pattern.matches("по.*", tmp)){
				} else if(Pattern.matches("с.*", tmp)){
				} else if(Pattern.matches("[0-9]{1,2}[.][0-9]{1,2}[,][0-9]{1,2}[.][0-9]{1,2}[,][0-9]{1,2}[.][0-9]{1,2}[,][0-9]{1,2}[.][0-9]{1,2}", tmp)){
					System.out.println("4 даты");
				} else if(Pattern.matches("[[0-9]{1,2}[.][0-9]{1,2}[,]]{3,3}[0-9]{1,2}[.][0-9]{1,2}[\\/][[0-9]{1,2}[.][0-9]{1,2}[,]]{3,3}[0-9]{1,2}[.][0-9]{1,2}", tmp)){
					System.out.println("8 дат");
				}

			}

		}
		System.out.println(added);
		System.out.println(doubleAdd);
		System.out.println(errorLog);
	}





    // ****************************//
    // ========================== //
    // ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ!!! //
    // ========================== //
    // ****************************//


    /**
     * Конвертирует во внутреннее представление формы занятия
     * @param aFoC пр лек или л.р.
     * @return тип перечилсения FormOfClass либо null, если не найдет соответствия
     */
	private static FormOfClass convertToEnumFormOfClass(String aFoC) {
		if(aFoC.equals("лек"))
			return FormOfClass.LEC;
		else if(aFoC.equals("пр"))
			return FormOfClass.PRAC;
		else if(aFoC.equals("л.р."))
			return FormOfClass.LABS;
		else return null;
	}

    /**
     * Конвертирует время во внутреннее представление
     * @param aTime строковое время
     * @return тип перечисления либо null
     */
	private static Time convertToEnumTime(String aTime){
		if(aTime.equals("8:00"))
			return Time.at08_00;
		else if(aTime.equals("9:40"))
			return Time.at09_40;
		else if(aTime.equals("11:30"))
			return Time.at11_30;
		else if(aTime.equals("13:10"))
			return Time.at13_10;
		else if(aTime.equals("15:00"))
			return Time.at15_00;
		else if(aTime.equals("16:40"))
			return Time.at16_40;
		else if(aTime.equals("18:15"))
			return Time.at18_15;
		else if(aTime.equals("19:45"))
			return Time.at19_45;
		else return null;
	}

    /**
     * Конвертирует строковое представление дня недели в статик поле int класса Calendar.
     */
	private static int convetToDayOfWeek(String aDay){
		aDay = aDay.toLowerCase();
		if(aDay.equals("пн"))
			return Calendar.MONDAY;
		else if(aDay.equals("вт"))
			return Calendar.TUESDAY;
		else if(aDay.equals("ср"))
			return Calendar.WEDNESDAY;
		else if(aDay.equals("чт"))
			return Calendar.THURSDAY;
		else if(aDay.equals("пт"))
			return Calendar.FRIDAY;
		else if(aDay.equals("сб"))
			return Calendar.SATURDAY;
		else return -1;
	}

    /**
     * Функция для сравнения названий предметов. Сравнивает по символьно.
     *
     * @param arg1
     *            , arg2 - строки которые нужно сравнить.
     * @return true, если более половины символов сошлись. При этом меньшая
     *         строка берется за основную.
     */
	private static boolean compareDiscipline(String arg1, String arg2, int percentMatch) {
		if (arg1 == null || arg2 == null)
			return false;
		char[] chr1 = arg1.toCharArray();
		char[] chr2 = arg2.toCharArray();
		char[] helper;
		if (chr1.length > chr2.length) { // mStr1 всегда будет больше по длинне или равен mStr2
			helper = chr1;
			chr1 = chr2;
			chr2 = helper;
			helper = null;
		}
		int result = 0, length = chr1.length;
		for (int i = 0; i < chr1.length; i++) {
			if (chr1[i] == (chr2[i]))
				result++;
		}
		if ((float) result / length >= percentMatch / 100.0F) // Делим на
            // длинную строку
            // для получения
            // адекватных
            // данных
			return true;
		else
			return false;
	}

    /**
     * Обрабатывает строку и удаляет табуляцию, переводы строки и лишние пробелы
     *
     * @param input
     *            - строка, которую нужно обработать
     * @return строку либо null при входной строке нулевой длинны
     */
	private static String splitStr(String input) {
		if (input == null || input.isEmpty())
			return "";
		input = input.replaceAll("\t", " ");
		input = input.replaceAll("\n", " ");
		input = input.replaceAll("\r", " ");
		String[] strMatrix = input.split(" ");
		String result = "";
		for (int i = 0; i < strMatrix.length; i++) {
			if (!strMatrix[i].isEmpty())
				result += (result.isEmpty() ? "" : " ") + strMatrix[i];
		}
		return result;
	}

    /**
     * Фунция для вытаскивания из строки имен групп
     *
     * @param arg0
     *            строка с разлицными данными, содержащая или не содержащая
     *            имена групп в формате ####, где #-цифра
     * @return массив с именами групп
     */
	private static String[] getGroupNames(String arg0) {
		if (arg0.isEmpty())
			return null;
		String resultStr = "";
		String[] resultMatrix = arg0.split(" ");
		for (int i = 0; i < resultMatrix.length; i++) {
			if (Pattern.matches("[0-9][0-9][0-9][0-9],*", resultMatrix[i])) {
				if (Pattern.matches("[0-9][0-9][0-9][0-9],", resultMatrix[i]))
					resultMatrix[i] = resultMatrix[i].substring(0, resultMatrix[i].length() - 1);
				resultStr += (resultStr.length() == 0 ? "" : " ") + resultMatrix[i];
			}
		}
		return resultMatrix = resultStr.split(" ");
	}

}