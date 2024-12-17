
package com.raven.form;

import com.raven.event.EventItem;
import com.raven.model.ModelItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.ImageIcon;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;


public class MenuFood extends javax.swing.JPanel {

    private FormHome home;
    private Animator animator;
    private Point animatorPoint;
    private ModelItem itemsSelect;
    public MenuFood() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        init();
        //  Animator bắt đầu từ animatePoint đến TagetPoint
        animator = PropertySetter.createAnimator(500, mainPanel, "imageLocation", animatorPoint, mainPanel.getTargetLocation());
        animator.addTarget(new PropertySetter(mainPanel, "imageSize", new Dimension(180, 120), mainPanel.getTargetSize()));
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void end() {
                mainPanel.setImageOld(null);
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
    }
    
    public void init(){
        home = new FormHome();
        //winButton.initEvent(this, background1);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);
        testData();
    }


    private void testData() {
        home.setEvent(new EventItem() {
            @Override
            public void itemClick(Component com, ModelItem item) {
                if (itemsSelect != null) {
                    mainPanel.setImageOld(itemsSelect.getImage());
                }
                if (itemsSelect != item) {
                    if (!animator.isRunning()) {
                        itemsSelect = item;
                        animatorPoint = getLocationOf(com);
                        mainPanel.setImage(item.getImage());
                        mainPanel.setImageLocation(animatorPoint);
                        mainPanel.setImageSize(new Dimension(180, 120));
                        mainPanel.repaint();
                        home.setSelected(com);
                        home.showItem(item);
                        animator.start();
                    }
                }
            }
        });
        int ID = 1;
        home.addItem(new ModelItem(ID++, "Thịt bò", "Thịt được cắt mỏng và bày cùng một số loại rau thơm như húng quế, ớt và hành", "Chipi Buffet", "Món nhúng", new ImageIcon(getClass().getResource("/Icons/Food/Aries/1.jpg"))));
        home.addItem(new ModelItem(ID++, "Dê nướng", "Đặc trưng của món này là thịt được nướng chín vàng với lớp da giòn, bên trong thịt mềm.", "Chipi Buffet", "Món đặc sản", new ImageIcon(getClass().getResource("/Icons/Food/Aries/2.jpg"))));
        home.addItem(new ModelItem(ID++, "Thịt lợn", "Thịt được cắt mỏng và bày cùng một số loại rau thơm như húng quế, ớt và hành", "Chipi Buffet", "Món nhúng", new ImageIcon(getClass().getResource("/Icons/Food/Aries/8.jpg"))));
        home.addItem(new ModelItem(ID++, "Cừu xóc lá cà ri", "Đây là một món đặc sản từ thịt cừu, được xào nhanh với lá cà ri và các loại gia vị. ", "Chipi Buffet", "Món xào", new ImageIcon(getClass().getResource("/Icons/Food/Aries/4.jpg"))));
        home.addItem(new ModelItem(ID++, "Thịt cừu nướng xiên", "Thịt được xiên vào que và nướng trên than hoa.", "Chipi Buffet", "Món nướng", new ImageIcon(getClass().getResource("/Icons/Food/Aries/9.jpg"))));
        home.addItem(new ModelItem(ID++, "Bít tết cừu", "Thịt được cắt thành miếng lớn, ướp gia vị và nướng trên chảo gang nóng.", "Chipi Buffet", "Món Âu", new ImageIcon(getClass().getResource("/Icons/Food/Aries/6.jpg"))));
        home.addItem(new ModelItem(ID++, "Cua hấp", "Cua được phủ một lớp gia vị màu đỏ cam, có thể là hỗn hợp của ớt bột, tiêu, tỏi, bơ và các loại gia vị khác.", "Chipi Buffet", "Hải sản", new ImageIcon(getClass().getResource("/Icons/Food/Cancer/29.jpg"))));
        home.addItem(new ModelItem(ID++, "Cua nướng phô mai", "Phần trên của món ăn được phủ một lớp phô mai vàng óng, tan chảy.", "Chipi Buffet", "Món nướng", new ImageIcon(getClass().getResource("/Icons/Food/Cancer/30.jpg"))));
        home.addItem(new ModelItem(ID++, "Cua hấp sốt cay", "Cua được phủ một lớp gia vị màu đỏ cam, có thể là hỗn hợp của ớt bột, tiêu, tỏi, bơ và các loại gia vị khác. ", "Chipi Buffet", "Hải sản", new ImageIcon(getClass().getResource("/Icons/Food/Cancer/31.jpg"))));
        home.addItem(new ModelItem(ID++, "Cua hấp", "Cua được phủ một lớp gia vị", "Chipi Buffet", "Hải sản", new ImageIcon(getClass().getResource("/Icons/Food/Cancer/32.jpg"))));
        home.addItem(new ModelItem(ID++, "Cua hấp", "Cua được phủ một lớp gia vị", "Chipi Buffet", "Hải sản", new ImageIcon(getClass().getResource("/Icons/Food/Cancer/33.jpg"))));
        home.addItem(new ModelItem(ID++, "Cơm chiên cua", "Thịt cua được xé nhỏ, phi thơm cùng hành lá, sau đó cho cơm vào đảo đều.", "Chipi Buffet", "Món chiên", new ImageIcon(getClass().getResource("/Icons/Food/Cancer/34.jpg"))));
        home.addItem(new ModelItem(ID++, "Thịt trâu hầm thuốc bắc", "Thịt được hầm nhừ cùng với các loại thuốc bắc và gia vị.", "Chipi Buffet", "Món đặc sản", new ImageIcon(getClass().getResource("/Icons/Food/Capricorn/63.jpg"))));
        home.addItem(new ModelItem(ID++, "Thịt luộc", "Thịt ba chỉ được luộc cùng với các loại gia vị như hành lá, gừng, tỏi để khử mùi hôi và tạo hương thơm đặc trưng. ", "Chipi Buffet", "Món ăn mặn", new ImageIcon(getClass().getResource("/Icons/Food/Leo/35.jpg"))));
        home.addItem(new ModelItem(ID++, "Kimchi-jeon", "Đây là một món ăn chiên, thường được làm từ kimchi đã lên men kết hợp với bột mì.", "Chipi Buffet", "Món ăn mặn", new ImageIcon(getClass().getResource("/Icons/Food/Leo/36.jpg"))));
        home.addItem(new ModelItem(ID++, "Tteokbokki", "Bánh gạo tteok có độ dai vừa phải, khi nấu chín sẽ mềm dẻo và rất thích thú khi ăn.", "Chipi Buffet", "Món ăn mặn", new ImageIcon(getClass().getResource("/Icons/Food/Leo/37.jpg"))));
        home.addItem(new ModelItem(ID++, "Budae jjigae", "Được biến tấu với nhiều loại nguyên liệu khác nhau, nhưng vẫn giữ được hương vị đặc trưng cay nồng, đậm đà.", "Chipi Buffet", "Món ăn mặn", new ImageIcon(getClass().getResource("/Icons/Food/Leo/38.jpg"))));
        home.addItem(new ModelItem(ID++, "Bulgogi", "Thịt bò được ướp với một loại sốt đậm đà, khi nướng lên sẽ tạo ra một hương vị ngọt ngào, đậm đà.", "Chipi Buffet", "Món ăn mặn", new ImageIcon(getClass().getResource("/Icons/Food/Leo/39.jpg"))));
        home.addItem(new ModelItem(ID++, "Sundubu Jjigae", " Món ăn ấm lòng và bổ dưỡng, thường được thưởng thức vào những ngày lạnh giá.", "Chipi Buffet", "Món ăn mặn", new ImageIcon(getClass().getResource("/Icons/Food/Leo/40.jpg"))));
        
        
    }

     private Point getLocationOf(Component com) {
        Point p = home.getPanelItemLocation();
        int x = p.x;
        int y = p.y;
        int itemX = com.getX();
        int itemY = com.getY();
        int left = 10;
        int top = 35;
        return new Point(x + itemX + left, y + itemY + top);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        header1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mainPanel = new com.raven.swing.MainPanel();

        background1.setPreferredSize(new java.awt.Dimension(1022, 544));

        header1.setOpaque(false);
        header1.setPreferredSize(new java.awt.Dimension(1022, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("MENU");

        javax.swing.GroupLayout header1Layout = new javax.swing.GroupLayout(header1);
        header1.setLayout(header1Layout);
        header1Layout.setHorizontalGroup(
            header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, header1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(0, 975, Short.MAX_VALUE))
        );
        header1Layout.setVerticalGroup(
            header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        mainPanel.setPreferredSize(new java.awt.Dimension(0, 510));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JPanel header1;
    private javax.swing.JLabel jLabel2;
    private com.raven.swing.MainPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
