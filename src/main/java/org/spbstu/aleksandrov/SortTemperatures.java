/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.spbstu.aleksandrov;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class SortTemperatures {

    // Быстродействие: Timsort
    //                 Лучшее: O(n)
    //                 Среднее, худшее: O(nlog(n))
    // Ресурсоемкость: O(n)
    public static void libSortTemperatures(Temperature[] t) {
        Arrays.sort(t);
    }

    // Быстродействие: O(n)
    // Ресурсоемкость: O(1)
    public static void fastestSortTemperatures(Temperature[] t) {

        int[] range = new int[7731];

        for (Temperature temperature : t) {
            int value = temperature.getValue();
            range[value + 2730] = range[value + 2730] + 1;
        }

        int k = 0;
        for (int i = 0; i < 7731; i++) {
            int amount = range[i];
            while (amount > 0) {
                t[k] = new Temperature(i - 2730);
                k++;
                amount--;
            }
        }
    }

    // Быстродействие: Запись всех значений по корзинам + сортировка корзин Timsort
    //                 Лучшее: O(n) + 774 * O(n/774) = O(n)
    //                 Среднее, худшее: O(n) + 774 * O(n/774 * log(n/774)) = O(nlog(n))Marks
    // Ресурсоемкость: O(n)
    //                 Запись в корзины O(n) + Timsort корзин в среднем требует O(n/774)
    public static void fastSortTemperatures(Temperature[] t) {

        List<List<Integer>> range = new ArrayList<>(774);

        for (int i = 0; i < 775; i++) {
            range.add(new ArrayList<>());
        }

        for (Temperature temperature : t) {
            int value = temperature.getValue();
            int index = value / 10 + 273;
            if (value > 0) index++;
            List<Integer> bucket = range.get(index);
            bucket.add(abs(value) % 10);
        }

        int k = 0;

        for (int i = 0; i < 775; i++) {
            int value = i - 273;
            if (value > 0) value--;
            value *= 10;
            List<Integer> bucket = range.get(i);
            if (i <= 273) bucket.sort(Collections.reverseOrder());
            else Collections.sort(bucket);
            for (Integer f : bucket) {
                if (i == 273) t[k] = new Temperature(value + f * -1);
                else if (i == 274)  t[k] = new Temperature(value + f);
                else t[k] = new Temperature(value + f * (abs(value) / value));
                k++;
            }
        }
    }

    // Быстродействие: Среднее, худшее: O(n^2)
    //                 Лучшее: O(n)
    // Ресурсоемкость: O(1)
    public static void insertionSortTemperatures(Temperature[] t) {
        for (int i = 1; i < t.length; i++) {
            Temperature current = t[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (t[j].compareTo(current) > 0) t[j+1] = t[j];
                else break;
            }
            t[j+1] = current;
        }
    }

    public static class Temperature implements Comparable<Object> {

        private final int sign;
        private final int integerPart;
        private final int fractionalPart;

        public Temperature(int i) {
            if (i > 5000 || i < -2730) throw new IllegalArgumentException();
            if (i == 0) sign = 1; else sign = abs(i) / i;
            integerPart = (i * sign) / 10;
            fractionalPart = abs(i) % 10;
        }

        @Override
        public String toString() {
            String s = "";
            if (sign == -1) s = "-";
            return s + integerPart + "." + fractionalPart;
        }

        @Override
        public int compareTo(@NotNull Object o) {
            if (!(o instanceof Temperature)) return -1;
            Temperature anotherT = (Temperature) o;
            if (this.integerPart * this.sign != anotherT.integerPart * anotherT.sign)
                return this.integerPart * this.sign - anotherT.integerPart * anotherT.sign;
            return this.fractionalPart * this.sign - anotherT.fractionalPart * anotherT.sign;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Temperature)) return false;
            Temperature t = (Temperature) o;
            return this.getValue() == t.getValue();
        }

        public int getValue() {
            return (integerPart * 10 + fractionalPart) * sign;
        }
    }
}
