package se.xfunserver.mainlyckohjul.utilities.list;

import java.util.List;

public class ListUtils {

    public static <T> List<T> shiftElement(List<T> list, T element, ShiftType shiftType) {
        int elementIndex = list.indexOf(element);

        int newIndex;
        if (shiftType.equals(ShiftType.LEFT)) {
            newIndex = elementIndex - 1;
            if (newIndex < 0) {
                return list;
            }
        } else if (shiftType.equals(ShiftType.RIGHT)) {
            newIndex = elementIndex + 1;
            if (newIndex >= list.size()) {
                return list;
            }
        } else {
            return list;
        }

        T copyElement = list.get(newIndex);

        list.set(elementIndex, copyElement);
        list.set(newIndex, element);
        return list;
    }

    public enum ShiftType {
        LEFT,
        RIGHT
    }
}

