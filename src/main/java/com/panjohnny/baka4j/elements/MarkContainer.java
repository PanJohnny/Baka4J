package com.panjohnny.baka4j.elements;

import java.util.List;

/**
 * @see <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/marks.md">Mark docs</a>
 */
public record MarkContainer(List<Mark> marks, BasicElement subject, String averageText) {

    /**
     *
     * @return average converted to double
     */
    public double getActualAverage() {
        return Double.parseDouble(averageText.contains(",") ? averageText.replace(',', '.') : averageText);
    }
}
