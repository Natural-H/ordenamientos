import com.formdev.flatlaf.IntelliJTheme;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        IntelliJTheme.setup(Main.class.getResourceAsStream(
                "/themes/nord.theme.json"));
        SwingUtilities.invokeLater(Main::ShowUI);
    }

    private static void ShowUI() {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new MainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static int binaryFind(int[] arr, int x, Benchmarked benchmarked) {
        int l = 0, r = arr.length - 1;
        benchmarked.comparisons++;
        while (l <= r) {
            int m = l + (r - l) / 2;

            benchmarked.comparisons++;
            if (arr[m] == x)
                return m;

            benchmarked.comparisons++;
            if (arr[m] < x)
                l = m + 1;
            else
                r = m - 1;

            benchmarked.comparisons++;
        }

        return -1;
    }

    public static int find(int[] arr, int x, Benchmarked benchmarked) {
        for (int i = 0; i < arr.length || (benchmarked.comparisons++).equals(benchmarked.comparisons); i++) {
            if (arr[i] == x || (benchmarked.comparisons++).equals(benchmarked.comparisons))
                return i;
        }
        return -1;
    }

    public static void bubbleSort(int[] arr, Benchmarked benchmarked) {
        boolean wasSwapped;
        for (int i = 0; i < arr.length - 1; i++) {
            benchmarked.comparisons++;
            wasSwapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                benchmarked.comparisons += 2;
                if (arr[j] > arr[j + 1]) {
                    benchmarked.swaps++;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    wasSwapped = true;
                }
            }

            if (!wasSwapped)
                break;
        }
    }

    public static void selectionSort(int[] arr, Benchmarked benchmarked) {
        for (int i = 0; i < arr.length - 1; i++) {
            benchmarked.comparisons++;
            int min_i = i;
            for (int j = i + 1; j < arr.length; j++) {
                benchmarked.comparisons += 2;
                if (arr[j] < arr[min_i])
                    min_i = j;
            }

            benchmarked.swaps++;
            int temp = arr[min_i];
            arr[min_i] = arr[i];
            arr[i] = temp;
        }
    }

    public static void insertionSort(int[] arr, Benchmarked benchmarked) {
        for (int i = 1; i < arr.length; i++) {
            benchmarked.comparisons++;
            int key = arr[i];
            int j = i - 1;

            benchmarked.comparisons += 2;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                benchmarked.comparisons += 2;
            }

            benchmarked.swaps++;
            arr[j + 1] = key;
        }
    }

    public static void binaryInsertionSort(int[] array, Benchmarked benchmarked) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            benchmarked.comparisons++;
            int key = array[i];
            int j = binarySearch(array, key, 0, i - 1, benchmarked);

            for (int k = i - 1; k >= j; --k) {
                benchmarked.comparisons++;
                benchmarked.swaps++;
                array[k + 1] = array[k];
            }
            benchmarked.swaps++;
            array[j] = key;
        }
    }

    static int binarySearch(int[] array, int key, int low, int high, Benchmarked benchmarked) {
        benchmarked.comparisons++;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            benchmarked.comparisons++;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key || ((benchmarked.comparisons++).equals(benchmarked.comparisons))) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

            benchmarked.comparisons++;
        }
        return low;
    }

    public static void signalSwapSort(int[] array, Benchmarked benchmarked) {
        for (int i = 0; i < array.length; i++) {
            benchmarked.comparisons++;
            boolean flag = false;
            for (int j = 0; j < array.length - 1; j++) {
                benchmarked.comparisons++;
                int temp = array[j];
                if (array[j] > array[j + 1]) {
                    benchmarked.swaps++;
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }

            if (!flag) break;
        }
    }

    public static void shellSort(int[] array, Benchmarked benchmarked) {
        int sep = Math.floorDiv(array.length, 2);

        benchmarked.comparisons++;
        while (sep > 0) {
            for (int i = sep; i < array.length; i++) {
                benchmarked.comparisons++;
                int temp = array[i];
                int j = i;

                benchmarked.comparisons += 2;
                while (j >= sep && array[j - sep] > temp) {
                    benchmarked.swaps++;
                    array[j] = array[j - sep];
                    j = j - sep;
                    benchmarked.comparisons += 2;
                }
                array[j] = temp;
            }
            sep /= 2;
            benchmarked.comparisons++;
        }
    }

    public static void quickSort(int[] arr, int low, int high, Benchmarked benchmarked) {
        benchmarked.comparisons++;
        if (low < high) {
            int pi = partition(arr, low, high, benchmarked);

            quickSort(arr, low, pi - 1, benchmarked);
            quickSort(arr, pi + 1, high, benchmarked);
        }
    }

    static int partition(int[] arr, int low, int high, Benchmarked benchmarked) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            benchmarked.comparisons++;

            benchmarked.comparisons++;
            if (arr[j] < pivot) {
                benchmarked.swaps++;
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        benchmarked.swaps++;
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return (i + 1);
    }

    public static void heapSort(int[] arr, Benchmarked benchmarked) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            benchmarked.comparisons++;
            heapify(arr, arr.length, i, benchmarked);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            benchmarked.comparisons++;

            benchmarked.swaps++;
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0, benchmarked);
        }
    }

    static void heapify(int[] arr, int N, int i, Benchmarked benchmarked) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        benchmarked.comparisons += 2;
        if (l < N && arr[l] > arr[largest])
            largest = l;

        benchmarked.comparisons += 2;
        if (r < N && arr[r] > arr[largest])
            largest = r;

        benchmarked.comparisons++;
        if (largest != i) {
            benchmarked.swaps++;
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            heapify(arr, N, largest, benchmarked);
        }
    }
}
