import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainPanel extends JPanel {

    private final DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ArrayList<Integer> integers = new ArrayList<>(IntStream.range(0, 1000).boxed().toList());

    public MainPanel() {
        setLayout(new GridBagLayout());
        Collections.shuffle(integers);

        dtm.setColumnIdentifiers(new String[]{
                "Datos",
                "Burbuja", "Selección", "Insersión Directa", "Insersión Binaria",
                "Intercambio Señal", "Shell", "Quick Sort", "Heap Sort"
        });

        DefaultListModel<Integer> integerDefaultListModel = new DefaultListModel<>();
        Arrays.stream(getArray()).forEach(integerDefaultListModel::addElement);
        doSorts();
        DefaultListModel<Integer> sortedDefaultListModel = new DefaultListModel<>();
        Arrays.stream(arrays[0]).forEach(sortedDefaultListModel::addElement);
        JTable table = new JTable(dtm);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(200);

        for (int i = 1; i < 9; i++) table.getColumnModel().getColumn(i).setPreferredWidth(140);
        table.setPreferredScrollableViewportSize(new Dimension(1200, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        JList<Integer> integerJList = new JList<>(integerDefaultListModel);
        add(new JScrollPane(integerJList), gbc);
        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(table), gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        JList<Integer> sortedJList = new JList<>(sortedDefaultListModel);
        add(new JScrollPane(sortedJList), gbc);

        int a1 = Arrays.stream(benchmarks).mapToInt(b -> b.swaps + b.comparisons).min().orElseThrow();
        int a = Arrays.stream(benchmarks).mapToInt(b -> b.swaps + b.comparisons).boxed().toList().indexOf(a1);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        add(new JLabel("Para este arreglo, el mejor método fue el de: " + intToMethod(a)) {
            {
                setFont(getFont().deriveFont(22f));
            }
        }, gbc);
        gbc.gridy++;
        gbc.weightx = 1;
        add(new JLabel("porque tuvo la menor cantidad de intercambios y comparaciones.") {
            {
                setFont(getFont().deriveFont(22f));
            }
        }, gbc);


        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        JTextField txtSearch = new JTextField();
        add(txtSearch, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1;
        gbc.gridx++;
        add(new JButton("Buscar") {
            {
                addActionListener(l -> {
                    Random random = new Random();

                    int[] arr = arrays[0];
                    Benchmarked seq = new Benchmarked();
                    Benchmarked bin = new Benchmarked();

                    try {
                        int toSearch = txtSearch.getText().isEmpty() ? random.nextInt(1000) : Integer.parseInt(txtSearch.getText());
                        int found = Main.binaryFind(arr, toSearch, bin);
                        int found1 = Main.find(arr, toSearch, seq);

                        if (Stream.of(found, found1).anyMatch(i -> i == -1)) {
                            JOptionPane.showMessageDialog(this, "¡Un método no pudo encontrar el valor!", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (found != found1) {
                            JOptionPane.showMessageDialog(this, "¡Las búsquedas difieren!", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        JOptionPane.showMessageDialog(this, String.format("# Comparaciones | Secuencial: %d - Busq. Binaria: %d: Índice: %d", seq.comparisons, bin.comparisons, found1), "Encontrado!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "¡No se pudo formatear!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        }, gbc);
    }

    String intToMethod(int i) {
        return switch (i) {
            case 0 -> "Burbuja";
            case 1 -> "Selección";
            case 2 -> "Insersión Directa";
            case 3 -> "Insersión Binaria";
            case 4 -> "Intercambio Señal";
            case 5 -> "Shell";
            case 6 -> "Quick Sort";
            case 7 -> "Heap Sort";
            default -> "";
        };
    }

    Benchmarked[] benchmarks = new Benchmarked[8];
    int[][] arrays = new int[1000][8];

    public void doSorts() {
        for (int i = 0; i < benchmarks.length; i++) benchmarks[i] = new Benchmarked();
        for (int i = 0; i < arrays[0].length; i++) arrays[i] = getArray();

        Main.bubbleSort(arrays[0], benchmarks[0]);
        Main.selectionSort(arrays[1], benchmarks[1]);
        Main.insertionSort(arrays[2], benchmarks[2]);
        Main.binaryInsertionSort(arrays[3], benchmarks[3]);
        Main.signalSwapSort(arrays[4], benchmarks[4]);
        Main.shellSort(arrays[5], benchmarks[5]);
        Main.quickSort(arrays[6], 0, arrays[6].length - 1, benchmarks[6]);
        Main.heapSort(arrays[7], benchmarks[7]);

        Object[] objects = new Object[benchmarks.length + 1];
        objects[0] = "Comparaciones";
        for (int i = 0; i < benchmarks.length; i++) objects[i + 1] = benchmarks[i].comparisons;
        dtm.addRow(objects);

        Object[] objects2 = new Object[benchmarks.length + 1];
        objects2[0] = "Intercambios";
        for (int i = 0; i < benchmarks.length; i++) objects2[i + 1] = benchmarks[i].swaps;
        dtm.addRow(objects2);
    }

    public int[] getArray() {
        return integers.stream().mapToInt(Integer::intValue).toArray();
    }
}
