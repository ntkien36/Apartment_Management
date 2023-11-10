# `Quản lý hệ thống cho thuê căn hộ trên nhiều tòa nhà`


## Cài đặt

1. Cài đặt IDE (InteliJ IDEA): Tải về và cài đặt theo hướng dẫn tại trang chủ của [InteliJ IDEA](https://www.jetbrains.com/idea/download/).

2. Cài đặt JavaFX và thiết lập trong IDE
    - Tải và cài đặt **JavaFX** tại [GluonHQ](https://gluonhq.com/products/javafx/).
    - Thêm ***Environment Variables***:
       Mở IDE (Inntellij), vào **Run**, chọn **Edit Configuration**, chọn **Add VM variables** và thêm:
            
            --module-path path/to/javafx-sdk-15.0.1/lib --add-modules javafx.controls,javafx.fxml
3. Cài đặt database
   Project sử dụng database MySQL, tải và cài đặt theo hướng dẫn tại [MySQL](https://www.mysql.com/downloads/).
    - Tạo database:
         ```
        mysql -u root -p
        create database itss;
        exit;
         ```
    - Import database:
        ```
        mysql -u root -p itss < all.sql
        ```
