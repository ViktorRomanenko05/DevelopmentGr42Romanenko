package de.ait.javalessons.homeworks.homework_5;

public enum Operations {
    LESS {
        public boolean apply(int x, int y) {
            return x < y;
        }
    },

    GREATER {
        public boolean apply(int x, int y) {
            return x > y;
        }
    },

    EQUAL {
        public boolean apply(int x, int y) {
            return x == y;
        }
    },

    LESS_OR_EQUAL {
        public boolean apply(int x, int y) {
            return x <= y;
        }
    },

    GREATER_OR_EQUAL {
        public boolean apply(int x, int y) {
            return x >= y;
        }
    };

    public abstract boolean apply(int x, int y);

}
