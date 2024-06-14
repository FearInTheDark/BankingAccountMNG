package Views.table_management;

import Data.DB_Manager;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import lombok.Getter;
import lombok.Setter;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;
import Views.table_management.form.Create;
import Models.ModelAccount;
import Views.table_management.swing.ButtonAction;
import Views.table_management.table.CheckBoxTableHeaderRenderer;
import Views.table_management.table.TableHeaderAlignment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static Data.DB_Manager.*;

@Getter
@Setter
public class Table extends JFrame {
    private ButtonAction cmdDelete, cmdEdit, cmdNew, cmdRefresh;
    private JLabel lbTitle;
    private JPanel panel;
    private JScrollPane scroll;
    private JTable table;
    private JTextField txtSearch;

    public Table() {
        initComponents();
        init();
//        increaseFontSize(this, 10);
//        table.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 20));
//        table.getTableHeader().setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 20));
//        table.setRowHeight(50);
//        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
//        System.out.println(table.getTableHeader().getFont().getName() + " - " + table.getTableHeader().getFont().getSize());
//        System.out.println(table.getFont().getName() + " - " + table.getFont().getSize());
    }

    public static void main(String[] args) {
        DB_Manager.initHibernate();
        FlatRobotoFont.installBasic();
        FlatLaf.registerCustomDefaultsSource("FlatLaf.theme");
        FlatMacLightLaf.setup();
//        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 20));
        setUIFont(new FontUIResource(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 20)));

        EventQueue.invokeLater(() -> new Table().setVisible(true));
    }

    private static void setUIFont(FontUIResource font) {
        UIManager.getLookAndFeelDefaults().entrySet().stream()
                .filter(entry -> entry.getKey().toString().toLowerCase().contains("font"))
                .forEach(entry -> UIManager.put(entry.getKey(), font));
    }

    private void init() {
        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:25;" + "background:$Table.background");

        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE,
                "height:30;"
                + "hoverBackground:null;"
//                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background;"
                + "font:bold;");

        table.putClientProperty(FlatClientProperties.STYLE,
                "rowHeight:30;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
                "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Table.background;");

        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +5;");

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon(getClass().getResource("/icons/ManageIcons/search.svg")));
        txtSearch.putClientProperty(FlatClientProperties.STYLE,
                "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "margin:5,20,5,20;"
                + "background:$Panel.background");

        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table));
        table.getTableHeader().setReorderingAllowed(true);

        loadData();
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        model.setRowCount(0);
        List<ModelAccount> list = queryAccounts();
        assert list != null;
        for (ModelAccount d : list) {
            model.addRow(d.toTableRow(table.getRowCount() + 1));
        }
        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Data has been loaded");
    }

    private void searchData(String search) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        model.setRowCount(0);
        List<ModelAccount> list = searchAccounts(search);
        assert list != null;
        for (ModelAccount d : list) {
            model.addRow(d.toTableRow(table.getRowCount() + 1));
        }
    }

    private void initComponents() {

        panel = new JPanel();
        scroll = new JScrollPane();
        table = new JTable();
        JSeparator separator = new JSeparator();
        txtSearch = new JTextField();
        lbTitle = new JLabel();
        cmdDelete = new ButtonAction();
        cmdEdit = new ButtonAction();
        cmdNew = new ButtonAction();
        cmdRefresh = new ButtonAction();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"SELECT", "#", "BANK NO", "PHONE NO", "NAME", "BIRTHDAY", "IDCARD", "VALID DATE", "ADDRESS", "PASSWORD", "CARD NO"}) {
            final Class<?>[] types = new Class[]
                    {Boolean.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class};
            final boolean[] canEdit = new boolean[]{true, false, false, false, false, false, false, false, false, false, false};

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scroll.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(1).setMaxWidth(40);
            table.getColumnModel().getColumn(2).setPreferredWidth(110);
            table.getColumnModel().getColumn(5).setPreferredWidth(60);
            table.getColumnModel().getColumn(6).setPreferredWidth(90);
            table.getColumnModel().getColumn(7).setPreferredWidth(60);
            table.getColumnModel().getColumn(8).setPreferredWidth(150);
            table.getColumnModel().getColumn(9).setPreferredWidth(50);
            table.getColumnModel().getColumn(10).setPreferredWidth(140);
        }

        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                txtSearchKeyReleased();
            }
        });

        lbTitle.setText("BANKING ACCOUNTS");

        cmdDelete.setText("Delete");
        cmdDelete.addActionListener(this::cmdDeleteAction);

        cmdEdit.setText("Edit");
        cmdEdit.addActionListener(this::cmdEditAction);

        cmdNew.setText("New");
        cmdNew.addActionListener(this::cmdNewAction);

        cmdRefresh.setText("Refresh");
        cmdRefresh.addActionListener(e -> loadData());

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 1563, Short.MAX_VALUE)
                .addComponent(separator)
                .addGroup(panelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout
                                        .createSequentialGroup()
                                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmdRefresh, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdNew, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdEdit, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdDelete, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(lbTitle))
                        .addGap(30, 30, 30)));
        panelLayout.setVerticalGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, panelLayout
                        .createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbTitle).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGap(20, 20, 20)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmdDelete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmdEdit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmdNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmdRefresh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGap(20, 20, 20)
                        .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                        .addGap(20, 20, 20)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)));

        pack();
        setLocationRelativeTo(null);
    }

    private void txtSearchKeyReleased() {
        searchData(txtSearch.getText().trim());
    }

    private void cmdNewAction(ActionEvent evt) {
        Create create = new Create();
        create.loadData(null);
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };
        String[] actions = new String[]{"Cancel", "Save"};
        GlassPanePopup.showPopup(new SimplePopupBorder(create, "Create Employee", actions, (pc, i) -> {
            if (i == 1) {
                // save
                try {
                    saveAccount(create.getData());
                    pc.closePopup();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Account has been created");
                    loadData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                pc.closePopup();
            }
        }), option);
    }

    private void cmdEditAction(ActionEvent evt) {
        List<ModelAccount> list = getSelectedData();
        if (list.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select account to edit");
            return;
        }
        if (list.size() == 1) {
            ModelAccount data = list.get(0);
            Create create = new Create();
            create.loadData(data);
            DefaultOption option = new DefaultOption() {
                @Override
                public boolean closeWhenClickOutside() {
                    return true;
                }
            };
            String[] actions = new String[]{"Cancel", "Update"};
            GlassPanePopup.showPopup(new SimplePopupBorder(create, "Edit Account [" + data.getFullName() + "]", actions, (pc, i) -> {
                if (i == 1) {
                    try {
                        ModelAccount dataEdit = create.getData();
                        dataEdit.setId(data.getId());
                        updateAccount(dataEdit);
                        pc.closePopup();
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Account has been updated");
                        loadData();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    pc.closePopup();
                }
            }), option);
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select only one account to edit");
        }
    }

    private void cmdDeleteAction(ActionEvent evt) {
        List<ModelAccount> list = getSelectedData();
        if (!list.isEmpty()) {
            DefaultOption option = new DefaultOption() {
                @Override
                public boolean closeWhenClickOutside() {
                    return true;
                }
            };
            String[] actions = new String[]{"Cancel", "Delete"};
            JLabel label = new JLabel("Are you sure to delete " + list.size() + " employee ?");
            label.setBorder(new EmptyBorder(0, 25, 0, 25));
            GlassPanePopup.showPopup(new SimplePopupBorder(label, "Confirm Delete", actions, (pc, i) -> {
                if (i == 1) {
                    for (ModelAccount d : list) {
                        deleteAccount(d);
                    }
                    pc.closePopup();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Employee has been deleted");
                    loadData();
                } else {
                    pc.closePopup();
                }
            }), option);
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select employee to delete");
        }
    }

    private List<ModelAccount> getSelectedData() {
        List<ModelAccount> list = new ArrayList<>();
        try {

            for (int i = 0; i < table.getRowCount(); ++i) {
                if ((boolean) table.getValueAt(i, 0)) {
                    SimpleDateFormat raw = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String id = table.getValueAt(i, 2).toString();
                    String phone = table.getValueAt(i, 3).toString();
                    String name = table.getValueAt(i, 4).toString();
                    String birthday = sdf.format(raw.parse(table.getValueAt(i, 5).toString()));
                    System.out.println(birthday);
                    String idcard = table.getValueAt(i, 6).toString();
                    String valid = sdf.format(raw.parse(table.getValueAt(i, 7).toString()));
                    String address = table.getValueAt(i, 8).toString();
                    String password = table.getValueAt(i, 9).toString();
                    String card = table.getValueAt(i, 10).toString();
                    list.add(new ModelAccount(id, phone, name, birthday, idcard, valid, address, password, card));
                }
            }
        } catch (ParseException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void increaseFontSize(Component component, int increaseAmount) {
        if (component instanceof JLabel || component instanceof JButton || component instanceof JTextField) {
            Font currentFont = component.getFont();
            Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + increaseAmount);
            component.setFont(newFont);
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                increaseFontSize(child, increaseAmount);
            }
        }
    }

}
