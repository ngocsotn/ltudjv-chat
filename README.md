## Lập trình ứng dụng java - Đồ án 4

---

### Họ và Tên: Nguyễn Thế Ngọc

---

#### Các chức năng của project:

-   Đăng ký tài khoản.
-   Đăng nhập với tài khoản đã đăng ký.
-   Chat.
-   Chat cùng lúc với nhiều người (được mở nhiều cửa sổ).
-   Lưu tài khoản đăng ký người dùng vào DB.
-   Server có ghi log từng client lại.

#### Link xem trực tuyến trên YouTube có thuyết trình về setup project và chạy: https://youtu.be/G2tBgJnHybo

#### hướng dẫn setup step-by-step (hướng dẫn step-by-step có hình ảnh ở file README.pdf):

-   Khởi động server, nhập các thông tin kết nối DB: IP, DB_Port, DB_name, DB_username, DB_password, nhập cổng host cho các client.
-   Nếu kết nối DB thành công, server sẽ vào màn hình Log, chờ các Client vào.
-   Khởi động client 1, bấm vào nút "setting" để thiết lập IP và port của server và lưu lại. Client này chưa có tài khoản, bấm vào nút "Register here!", nhập tài khoản, mật khẩu và nhập lại mật khẩu. bấm đăng ký. nếu thành công sẽ có thông báo thành công, nếu trùng username sẽ có thông báo.
-   Client sau khi đăng ký, trở lại màn hình đăng nhập, nhập username và password, bấm "Confirm", nếu đúng thông tin, server sẽ lưu lại log và cho client vào, bên màn hình client sẽ hiển thị màn hình danh sách online.
-   Client 2 khởi động, Client này đã có tài khoản từ trước nên login luôn.
-   Sau khi client 2 thành công login, client 1 bấm nút "refresh" danh sách người online sẽ thấy Client 2, ngược lại, Client 2 sau khi bấm "refresh" cũng thấy client 1
-   Tại client 2, bấm chọn vào nickname của Client 1 trong danh sách, bấm nút "chat with this user", 1 màn hình chat sẽ hiện lên.
-   Tương tự client 1, chọn nickname của Client 2 trong danh sách, bấm nút "chat with this user", 1 màn hình chat sẽ hiện lên.
-   ở đây cả 2 đã có thể nhắn tin cho nhau, nếu có nhiêu user, thì chọn nhiều lần "chat with this user", sẽ có thể chat cùng lúc nhiều người, lưu ý là hệ thống chỉ cho mở 1 windows với 1 người, không thể mở 2 windows để chat chỉ với 1 người.
