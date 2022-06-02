package hu.progmatic;

import hu.progmatic.model.FuelPriceChange;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 2. feladat
            List<FuelPriceChange> fuelPriceChanges = loadFuelPriceChanges("uzemanyag.txt");

            // 3. feladat
            System.out.print("3. feladat: ");
            System.out.println("Változások száma: " + fuelPriceChanges.size());

            // 4. feladat
            System.out.print("4. feladat: ");
            int min = minDifference(fuelPriceChanges);
            System.out.println("A legkisebb különbség: " + min);

            // 5. feladat
            System.out.print("5. feladat: ");
            int counter = countDifferences(fuelPriceChanges, min);
            System.out.println("A legkisebb különbség előfordulása: " + counter);

            // 6. feladat
            System.out.print("6. feladat: ");
            boolean leapDayFound = findLeapDay(fuelPriceChanges);

            if (leapDayFound) {
                System.out.println("Volt változás szökőnapon!");
            } else {
                System.out.println("Nem volt változás szökőnapon!");
            }

            // 7. feladat
            saveFuelPriceChanges(fuelPriceChanges, "euro.txt");

            // 8. feladat
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int year;

            do {
                System.out.print("8. feladat: Kérem adja meg az évszámot [2011..2016]: ");
                year = Integer.parseInt(reader.readLine());
            } while (year < 2011 || year > 2016);

            // 10. feladat
            System.out.print("10. feladat: ");
            int max = getMaxPeriod(fuelPriceChanges, year);
            System.out.printf("%d évben a leghosszabb időszak %d nap volt.\n", year, max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<FuelPriceChange> loadFuelPriceChanges(String path) throws IOException {
        List<FuelPriceChange> fuelPriceChanges = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                fuelPriceChanges.add(new FuelPriceChange(line));
            }
        }

        return fuelPriceChanges;
    }

    // 4. feladat
    private static int minDifference(Collection<FuelPriceChange> fuelPriceChanges) {
        int min = Integer.MAX_VALUE;

        for (FuelPriceChange fuelPriceChange : fuelPriceChanges) {
            if (fuelPriceChange.getDifference() < min) {
                min = fuelPriceChange.getDifference();
            }
        }

        return min;
    }

    // 5. feladat
    private static int countDifferences(Collection<FuelPriceChange> fuelPriceChanges, int difference) {
        int counter = 0;

        for (FuelPriceChange fuelPriceChange : fuelPriceChanges) {
            if (fuelPriceChange.getDifference() == difference) {
                counter++;
            }
        }

        return counter;
    }

    // 6. feladat
    private static boolean findLeapDay(Collection<FuelPriceChange> fuelPriceChanges) {
        for (FuelPriceChange fuelPriceChange : fuelPriceChanges) {
            if (fuelPriceChange.isLeapDay()) {
                return true;
            }
        }

        return false;
    }

    // 10. felada
    private static int getMaxPeriod(Collection<FuelPriceChange> fuelPriceChanges, int year) {
        FuelPriceChange lastChange = null;
        int max = Integer.MIN_VALUE;

        for (FuelPriceChange fuelPriceChange : fuelPriceChanges) {
            if (fuelPriceChange.getDate().getYear() == year) {
                if (lastChange != null) {
                    int days = fuelPriceChange.getDaysPassedSince(lastChange);

                    if (days > max) {
                        max = days;
                    }
                }

                lastChange = fuelPriceChange;
            }
        }

        return max;
    }

    private static void saveFuelPriceChanges(List<FuelPriceChange> fuelPriceChanges, String path)
            throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (FuelPriceChange fuelPriceChange : fuelPriceChanges) {
                writer.println(fuelPriceChange.toStringEUR());
            }
        }
    }
}
