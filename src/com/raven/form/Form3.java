
package com.raven.form;

import com.raven.transitions.TransitionsForm;


public class Form3 extends TransitionsForm {


    public Form3() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        img = new javax.swing.JLabel();
        header = new javax.swing.JLabel();
        wel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        footer = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listMember = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        header1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1022, 544));
        setLayout(new java.awt.BorderLayout());

        img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/hinh-anh-ve-do-an-25.jpg"))); // NOI18N

        header.setFont(new java.awt.Font("Papyrus", 1, 20)); // NOI18N
        header.setForeground(new java.awt.Color(108, 91, 123));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("ABOUT US");

        wel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        wel.setForeground(new java.awt.Color(63, 63, 63));
        wel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        wel.setText("<html>  Welcome to  <i style=\"color:rgb(108,91,123);font-size:14px;\">  Buffet Mũ Rơm<i> </html>");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txt.setEditable(false);
        txt.setBackground(new java.awt.Color(255, 255, 255));
        txt.setColumns(20);
        txt.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt.setForeground(new java.awt.Color(89, 89, 89));
        txt.setLineWrap(true);
        txt.setRows(5);
        txt.setText("Nhà hàng của chúng tôi là biểu tượng của sự thanh lịch và sang trọng. Với kiến trúc đẹp mắt, nội thất đậm chất cổ điển và sự chú trọng đến từng chi tiết nhỏ, chúng tôi tạo ra một không gian lịch sự và đẳng cấp.\nĐội ngũ đầu bếp chúng tôi là những nghệ nhân ẩm thực, tạo ra những tác phẩm nghệ thuật trên đĩa. Với sự khéo léo trong việc kết hợp các hương vị, chúng tôi mang đến cho thực khách những trải nghiệm ẩm thực độc đáo và tinh tế. Chúng tôi sử dụng các nguyên liệu tươi ngon và cao cấp nhất, kết hợp với sự sáng tạo và kỹ thuật chế biến tinh tế để tạo nên những món ăn truyền thống với một phong cách hiện đại và độc đáo.\nNhà hàng của chúng tôi là một nơi lý tưởng để tụ họp với gia đình và bạn bè, nơi mà từng chi tiết đều được chăm chút tỉ mỉ. \nĐội ngũ nhân viên phục vụ của chúng tôi, với sự chuyên nghiệp và tận tâm, sẽ đảm bảo rằng mỗi khách hàng được đối xử với sự chu đáo và sự phục vụ tận tâm. \nHãy đến và khám phá không gian thanh lịch, tinh tế và sang trọng của nhà hàng chúng tôi. Chúng tôi cam kết đem đến cho quý khách một trải nghiệm ẩm thực đẳng cấp và không gian lịch sự, để bạn có thể thưởng thức một bữa ăn ngon và có được những kỷ niệm chưa từng có.");
        txt.setWrapStyleWord(true);
        txt.setBorder(null);
        txt.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txt.setFocusable(false);
        txt.setRequestFocusEnabled(false);
        txt.setSelectionColor(new java.awt.Color(255, 255, 255));
        txt.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(txt);

        footer.setBackground(new java.awt.Color(255, 255, 255));
        footer.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        footer.setForeground(new java.awt.Color(63, 63, 63));
        footer.setText("Since : 2023 - Co-founder by The Dreamers Team ");

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setFocusable(false);

        listMember.setColumns(20);
        listMember.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        listMember.setForeground(new java.awt.Color(89, 89, 89));
        listMember.setRows(5);
        listMember.setText("Team Members :\nTrần Thị Huyền Trang ");
        listMember.setBorder(null);
        listMember.setFocusable(false);
        jScrollPane3.setViewportView(listMember);

        jSeparator1.setBackground(new java.awt.Color(76, 76, 76));

        header1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        header1.setForeground(new java.awt.Color(89, 89, 89));
        header1.setText("Tastes like a bit of heaven on your tongue.");
        header1.setToolTipText("");

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 992, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(background1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(header1)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(wel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2)
                        .addComponent(footer)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 424, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(background1Layout.createSequentialGroup()
                            .addComponent(header)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(header1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(wel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(footer)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane3)))
                    .addContainerGap()))
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLabel footer;
    private javax.swing.JLabel header;
    private javax.swing.JLabel header1;
    private javax.swing.JLabel img;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea listMember;
    private javax.swing.JTextArea txt;
    private javax.swing.JLabel wel;
    // End of variables declaration//GEN-END:variables
}
