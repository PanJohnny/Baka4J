package com.panjohnny.baka4j.elements;

/**
 * @see <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/homework.md">Homework docs</a>
 */
public record Homework(String ID, String dateStart, String dateEnd, String content, String notice, boolean done, boolean closed, boolean electronic, BasicElement clazz, BasicElement group, BasicElement subject, BasicElement teacher) {

}
