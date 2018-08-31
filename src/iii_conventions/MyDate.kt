package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) {
            return year - other.year
        }
        if (month != other.month) {
            return month - other.month
        }
        return dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(start = this, endInclusive = other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(multiple: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, multiple)
}

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return this.addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(rti: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(rti.ti, rti.n)
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    operator fun contains(d: MyDate): Boolean {
        return d >= start && d <= endInclusive
    }

    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this);
    }
}

class DateIterator(val range: DateRange) : Iterator<MyDate> {
    var current: MyDate = range.start

    override fun next(): MyDate {
        val result = current
        if (hasNext()) {
            current = current.nextDay()
        }
        return result
    }

    override fun hasNext(): Boolean {
        return current <= range.endInclusive
    }
}
