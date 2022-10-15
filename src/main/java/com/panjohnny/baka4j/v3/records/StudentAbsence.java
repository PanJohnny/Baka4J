package com.panjohnny.baka4j.v3.records;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public record StudentAbsence(double percentageThreshold, Absence[] absences, AbsencePerSubject[] absencesPerSubject) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentAbsence that = (StudentAbsence) o;

        if (Double.compare(that.percentageThreshold, percentageThreshold) != 0) return false;
        if (!Arrays.equals(absences, that.absences)) return false;
        return Arrays.equals(absencesPerSubject, that.absencesPerSubject);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(percentageThreshold);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(absences);
        result = 31 * result + Arrays.hashCode(absencesPerSubject);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StudentAbsence.class.getSimpleName() + "[", "]")
                .add("percentageThreshold=" + percentageThreshold)
                .add("absences=" + Arrays.toString(absences))
                .add("absencesPerSubject=" + Arrays.toString(absencesPerSubject))
                .toString();
    }

    public record Absence(String date, int unsolved, int ok, int missed, int late, int soon, int school, int distanceTeaching) {
        @Override
        public String toString() {
            return new StringJoiner(", ", Absence.class.getSimpleName() + "[", "]")
                    .add("date='" + date + "'")
                    .add("unsolved=" + unsolved)
                    .add("ok=" + ok)
                    .add("missed=" + missed)
                    .add("late=" + late)
                    .add("soon=" + soon)
                    .add("school=" + school)
                    .add("distanceTeaching=" + distanceTeaching)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Absence absence = (Absence) o;

            if (unsolved != absence.unsolved) return false;
            if (ok != absence.ok) return false;
            if (missed != absence.missed) return false;
            if (late != absence.late) return false;
            if (soon != absence.soon) return false;
            if (school != absence.school) return false;
            if (distanceTeaching != absence.distanceTeaching) return false;
            return Objects.equals(date, absence.date);
        }

        @Override
        public int hashCode() {
            int result = date != null ? date.hashCode() : 0;
            result = 31 * result + unsolved;
            result = 31 * result + ok;
            result = 31 * result + missed;
            result = 31 * result + late;
            result = 31 * result + soon;
            result = 31 * result + school;
            result = 31 * result + distanceTeaching;
            return result;
        }
    }

    public record AbsencePerSubject(String subjectName, int lessonsCount, int base, int late, int soon, int school, int distanceTeaching) {
        @Override
        public String toString() {
            return new StringJoiner(", ", AbsencePerSubject.class.getSimpleName() + "[", "]")
                    .add("subjectName='" + subjectName + "'")
                    .add("lessonsCount=" + lessonsCount)
                    .add("base=" + base)
                    .add("late=" + late)
                    .add("soon=" + soon)
                    .add("school=" + school)
                    .add("distanceTeaching=" + distanceTeaching)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AbsencePerSubject that = (AbsencePerSubject) o;

            if (lessonsCount != that.lessonsCount) return false;
            if (base != that.base) return false;
            if (late != that.late) return false;
            if (soon != that.soon) return false;
            if (school != that.school) return false;
            if (distanceTeaching != that.distanceTeaching) return false;
            return Objects.equals(subjectName, that.subjectName);
        }

        @Override
        public int hashCode() {
            int result = subjectName != null ? subjectName.hashCode() : 0;
            result = 31 * result + lessonsCount;
            result = 31 * result + base;
            result = 31 * result + late;
            result = 31 * result + soon;
            result = 31 * result + school;
            result = 31 * result + distanceTeaching;
            return result;
        }
    }
}
