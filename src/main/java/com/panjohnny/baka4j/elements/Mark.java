package com.panjohnny.baka4j.elements;

/**
 * @see <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks.md">Mark docs</a>
 */
public record Mark(String markDate, String editDate, String caption, String theme, String markText, String teacherId, String type, String typeNote, int weight, String subjectId, boolean isNew, boolean isPoints, String calculatedMarkText, String classRankText, String ID, String pointsText, int maxPoints) {

    /**
     *
     * @return returns the actual value of mark like 1 if the mark is 1- the mark returned will be negative
     */
    public int getActualMark() {
        if(markText.endsWith("-")) {
            return Integer.parseInt('-'+markText.replace('-', ' ').trim());
        } else {
            return Integer.parseInt(markText);
        }
    }
}
