package GUI;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class TaskManagerGUI extends JFrame implements ActionListener {

    private JPanel sort_SearchPanel = new JPanel();
    private JPanel refresh_CancelPanel = new JPanel();


    private JLabel infoLabel = new JLabel("<html>If selected programme opening, no sound and pop up window will be made when stopwatch times up.<br>Check guide for more information.<html>");
    private JLabel selectedLabel = new JLabel("<html><p style=\"width:400px\">" + "<b>You have selected:<b>" + "<br>" + "</p></html>");
    private JLabel whiteListLabel = new JLabel("<html><p style=\"width:400px\">" + "<b>In White List:<b>" + "<br>" + "</p></html>");
    private JLabel searchLabel = new JLabel("Search Programme:");

    private JButton addButton = new JButton("Add To White List");
    private JButton delButton = new JButton("Remove From White List");
    private JButton refreshButton = new JButton("Refresh");
    private JButton cancelButton = new JButton("Cancel");

    private JButton sortButton = new JButton("Sort List in Desc Order");

    private JTextField searchField = new JTextField(20);

    private MainPageGUI frame;

    private TaskManager taskManager = new TaskManager();


    private DefaultListModel listModel = new DefaultListModel();
    private JList<String> progList = new JList<String>(listModel);
    JScrollPane listScroller = new JScrollPane(progList);

    private ArrayList<String> whiteList = new ArrayList<String>();
    private ArrayList<String> taskList = new ArrayList<String>();

    public ArrayList<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(ArrayList<String> newWhiteList) {
        whiteList = newWhiteList;
    }

    public void checkWhiteListTaskGUI(MainPageGUI frame) {
        frame.setWhiteListMain(taskManager.checkWhiteList(whiteList));
        //System.out.println(whiteList);
    }

    public void updateTaskList() {
        taskManager.updateTaskList();
        taskList = taskManager.getTaskList();
        listModel.removeAllElements();
        for (String prog : taskList) {
            listModel.addElement(prog);
        }
        pack();
    }

    public void updateTaskListSort() {
        ArrayList<String> listItmes = new ArrayList<String>();
        for (int i = 0; i < listModel.getSize(); i++) {
            listItmes.add(listModel.getElementAt(i).toString());
        }
        Collections.reverse(listItmes);
        listModel.removeAllElements();
        for (String prog : listItmes) {
            listModel.addElement(prog);
        }
        selectedLabel.setText("<html><p style=\"width:400px\">" + "<b>You have selected:</b>" + "<br>" + "</p></html>");
    }

    public TaskManagerGUI(MainPageGUI frame) {
        super("Task Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setResizable(false);

        this.frame = frame;
        //System.out.println(frame.dataJson.getAllQuiteJson());
        whiteList = frame.dataJson.getWhiteListJson();
        String whiteListName = "";
        for (String name : whiteList) {
            whiteListName += name + ", ";
        }
        whiteListLabel.setText("<html><p style=\"width:400px\">" + "<b>In White List: </b>" + "<br>" + whiteListName + "</p></html>");

        setLayout(new GridBagLayout());

        progList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        progList.setLayoutOrientation(JList.VERTICAL);

        sort_SearchPanel.setLayout(new GridBagLayout());

        GridBagConstraints s_c0 = new GridBagConstraints();
        s_c0.gridx = 1;
        s_c0.gridy = 0;
        s_c0.gridwidth = 1;
        s_c0.gridheight = 1;
        s_c0.weightx = 0;
        s_c0.weighty = 0;
        s_c0.fill = GridBagConstraints.NONE;
        s_c0.anchor = GridBagConstraints.CENTER;
        s_c0.insets = new Insets(10, 10, 10, 10);
        sort_SearchPanel.add(sortButton, s_c0);

        GridBagConstraints s_c1 = new GridBagConstraints();
        s_c1.gridx = 0;
        s_c1.gridy = 2;
        s_c1.gridwidth = 2;
        s_c1.gridheight = 1;
        s_c1.weightx = 0;
        s_c1.weighty = 0;
        s_c1.fill = GridBagConstraints.NONE;
        s_c1.anchor = GridBagConstraints.WEST;
        s_c1.insets = new Insets(10, 0, 10, 10);
        sort_SearchPanel.add(searchField, s_c1);

        GridBagConstraints s_c2 = new GridBagConstraints();
        s_c2.gridx = 0;
        s_c2.gridy = 1;
        s_c2.gridwidth = 2;
        s_c2.gridheight = 1;
        s_c2.weightx = 0;
        s_c2.weighty = 0;
        s_c2.fill = GridBagConstraints.NONE;
        s_c2.anchor = GridBagConstraints.WEST;
        s_c2.insets = new Insets(20, 0, 0, 0);
        sort_SearchPanel.add(searchLabel, s_c2);

        refresh_CancelPanel.setLayout(new GridBagLayout());

        GridBagConstraints r_c0 = new GridBagConstraints();
        r_c0.gridx = 0;
        r_c0.gridy = 0;
        r_c0.gridwidth = 1;
        r_c0.gridheight = 1;
        r_c0.weightx = 0;
        r_c0.weighty = 0;
        r_c0.fill = GridBagConstraints.NONE;
        r_c0.anchor = GridBagConstraints.CENTER;
        r_c0.insets = new Insets(10, 10, 0, 10);
        refresh_CancelPanel.add(refreshButton, r_c0);

        GridBagConstraints r_c1 = new GridBagConstraints();
        r_c1.gridx = 0;
        r_c1.gridy = 1;
        r_c1.gridwidth = 0;
        r_c1.gridheight = 1;
        r_c1.weightx = 0;
        r_c1.weighty = 0;
        r_c1.fill = GridBagConstraints.NONE;
        r_c1.anchor = GridBagConstraints.CENTER;
        r_c1.insets = new Insets(10, 10, 10, 10);
        refresh_CancelPanel.add(cancelButton, r_c1);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                if (!searchField.getText().equals("")) {
                    search();
                } else {
                    listModel.removeAllElements();
                    for (String prog : taskList) {
                        listModel.addElement(prog);
                    }
                    pack();
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (!searchField.getText().equals("")) {
                    search();
                } else {
                    listModel.removeAllElements();
                    for (String prog : taskList) {
                        listModel.addElement(prog);
                    }
                    pack();
                }
            }

            public void insertUpdate(DocumentEvent e) {
                if (!searchField.getText().equals("")) {
                    search();
                } else {
                    listModel.removeAllElements();
                    for (String prog : taskList) {
                        listModel.addElement(prog);
                    }
                    pack();
                }
            }

            public void search() {
                listModel.removeAllElements();
                for (String prog : taskList) {
                    if (prog.toLowerCase().contains(searchField.getText().toLowerCase())) {
                        listModel.addElement(prog);
                    }
                }
                selectedLabel.setText("<html><p style=\"width:400px\">" + "<b>You have selected:<b>" + "<br>" + "</p></html>");
                pack();
            }
        });

        progList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        taskManager.updateTaskList();

        taskList = taskManager.getTaskList();

        for (String prog : taskList) {
            listModel.addElement(prog);
        }

        //----------------- Set Font Properties -----------------//
        infoLabel.setFont(new Font("Verdana ", Font.PLAIN, 18));
        selectedLabel.setFont(new Font("Verdana ", Font.PLAIN, 16));
        whiteListLabel.setFont(new Font("Verdana ", Font.PLAIN, 16));

        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.gridwidth = 2;
        c0.gridheight = 1;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.WEST;
        c0.insets = new Insets(10, 10, 10, 10);
        add(infoLabel, c0);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        c1.weightx = 0;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.NONE;
        c1.anchor = GridBagConstraints.CENTER;
        c1.insets = new Insets(10, 10, 10, 10);
        add(listScroller, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 2;
        c2.gridwidth = 1;
        c2.gridheight = 1;
        c2.weightx = 0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.NONE;
        c2.anchor = GridBagConstraints.WEST;
        c2.insets = new Insets(10, 10, 10, 0);
        add(selectedLabel, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 3;
        c3.gridwidth = 1;
        c3.gridheight = 1;
        c3.weightx = 0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.WEST;
        c3.insets = new Insets(10, 10, 10, 0);
        add(whiteListLabel, c3);

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 0;
        c4.gridy = 4;
        c4.gridwidth = 1;
        c4.gridheight = 1;
        c4.weightx = 0;
        c4.weighty = 0;
        c4.fill = GridBagConstraints.NONE;
        c4.anchor = GridBagConstraints.CENTER;
        c4.insets = new Insets(0, -150, 10, 0);
        add(addButton, c4);

        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 0;
        c5.gridy = 4;
        c5.gridwidth = 1;
        c5.gridheight = 1;
        c5.weightx = 0;
        c5.weighty = 0;
        c5.fill = GridBagConstraints.NONE;
        c5.anchor = GridBagConstraints.CENTER;
        c5.insets = new Insets(0, 200, 10, 0);
        add(delButton, c5);

        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 1;
        c6.gridy = 4;
        c6.gridwidth = 1;
        c6.gridheight = 1;
        c6.weightx = 0;
        c6.weighty = 0;
        c6.fill = GridBagConstraints.NONE;
        c6.anchor = GridBagConstraints.CENTER;
        c6.insets = new Insets(0, 10, 10, 10);
        add(refresh_CancelPanel, c6);

        GridBagConstraints c8 = new GridBagConstraints();
        c8.gridx = 1;
        c8.gridy = 1;
        c8.gridwidth = 1;
        c8.gridheight = 1;
        c8.weightx = 0;
        c8.weighty = 0;
        c8.fill = GridBagConstraints.NONE;
        c8.anchor = GridBagConstraints.CENTER;
        c8.insets = new Insets(0, 0, 0, 10);
        add(sort_SearchPanel, c8);

        pack();
        //setResizable(false);

        cancelButton.addActionListener(this);
        addButton.addActionListener(this);
        delButton.addActionListener(this);
        refreshButton.addActionListener(this);
        sortButton.addActionListener(this);

        progList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                String[] str = progList.getSelectedValuesList().toArray(new String[]{});
                String selected = "";
                for (String s : str) {
                    selected += s + ", ";
                }
                selectedLabel.setText("<html><p style=\"width:400px\">" + "<b>You have selected:</b>" + "<br>" + selected + "</p></html>");
                pack();
            }
        });

    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        //----------------- cancelButton -----------------//
        if (source == cancelButton) {
            this.dispose();
        } //----------------- addButton -----------------//
        else if (source == addButton) {
            String[] str = progList.getSelectedValuesList().toArray(new String[]{});
            for (String s : str) {
                if (!whiteList.contains(s) && !s.equals("")) {
                    whiteList.add(s);
                }
            }
            String whiteListName = "";
            for (String name : whiteList) {
                whiteListName += name + ", ";

            }
            whiteListLabel.setText("<html><p style=\"width:400px\">" + "<b>In White List: </b>" + "<br>" + whiteListName + "</p></html>");

            frame.setWhiteListMain(taskManager.checkWhiteList(whiteList));
            frame.dataJson.setWhiteListJson(whiteList);
            pack();
        } //----------------- delButton -----------------//
        else if (source == delButton) {
            String[] str = progList.getSelectedValuesList().toArray(new String[]{});
            for (String s : str) {
                if (whiteList.contains(s) && !s.equals("")) {
                    whiteList.remove(s);
                }
            }
            String whiteListName = "";
            for (String name : whiteList) {
                whiteListName += name + ", ";
            }
            whiteListLabel.setText("<html><p style=\"width:400px\">" + "<b>In White List: </b>" + "<br>" + whiteListName + "</p></html>");
            frame.setWhiteListMain(taskManager.checkWhiteList(whiteList));
            frame.dataJson.setWhiteListJson(whiteList);
            pack();
        } //----------------- refreshButton -----------------//
        else if (source == refreshButton) {
            updateTaskList();
        } //----------------- sortButton -----------------//
        else if (source == sortButton) {
            updateTaskListSort();
            if (sortButton.getText().equals("Sort List in Desc Order")) {
                sortButton.setText("Sort List in Asce Order");
            } else {
                sortButton.setText("Sort List in Desc Order");
            }
        }

    }

//    public void itemStateChanged(ItemEvent e) {
//        Object source = e.getSource();
//        if (source == progList) {
//            String str[] = progList.getSelectedItems();
//            String selected = "";
//            for (String s : str) {
//                selected += s + ", ";
//                selectedLabel.setText("<html><p style=\"width:400px\">" + "<b>You have selected:</b>" + "<br>" + selected + "</p></html>");
//                pack();
//            }
//        }
}

