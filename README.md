# TỔNG HỢP KIẾN THỨC ANDROID

## 1. Nền móng Giao diện (XML & Cơ bản)

### 1.1. Các thành phần chính của Android (Tứ đại Component)
Bất kỳ ứng dụng Android nào cũng được xây dựng từ 4 khối thành phần cốt lõi. Bạn có thể thiếu 1 trong 4, nhưng không thể có ứng dụng nếu thiếu tất cả:

* **Activity (Giao diện màn hình):** Đây là thành phần duy nhất có giao diện người dùng (UI). Mỗi Activity đại diện cho một màn hình độc lập mà người dùng có thể nhìn thấy và tương tác (như màn hình Đăng nhập, màn hình Danh sách).
* **Service (Dịch vụ chạy ngầm):** Trái ngược với Activity, Service không hề có giao diện. Nó chuyên dùng để thực hiện các tác vụ nặng, kéo dài ở dưới nền (background).
    * *Lưu ý phỏng vấn:* Cần phân biệt **Background Service** (Chạy ngầm hoàn toàn, dễ bị hệ điều hành kill nếu thiếu RAM) và **Foreground Service** (Chạy ngầm nhưng phải hiển thị một Notification không thể tắt để người dùng biết, ví dụ: App phát nhạc, App đếm bước chân).
* **Broadcast Receiver (Bộ thu phát sóng):** Đóng vai trò như một "cái ăng-ten" chuyên lắng nghe các thông báo toàn hệ thống hoặc từ các app khác. Ví dụ: Pin yếu, Có tin nhắn SMS tới.
    * *Lưu ý phỏng vấn:* Có 2 cách đăng ký: **Tĩnh** (Khai báo trong file Manifest) và **Động** (Viết code `registerReceiver` trực tiếp trong Activity, khi Activity chết thì ăng-ten cũng tắt).
* **Content Provider (Nhà cung cấp nội dung):** Là cầu nối để quản lý và chia sẻ dữ liệu an toàn giữa các ứng dụng với nhau. Ví dụ: Ứng dụng Zalo muốn lấy số điện thoại, nó phải đi qua Content Provider của ứng dụng "Danh bạ" mặc định trên máy.

---

### 1.2. Thành phần kết nối (The Glue)
4 thành phần trên là những khối độc lập. Để chúng nói chuyện được với nhau, Android sử dụng một khái niệm sống còn:

* **Intent (Người đưa thư):** Là một đối tượng mang thông điệp yêu cầu một hành động từ thành phần khác.
    * **Explicit Intent (Tường minh):** Chỉ đích danh màn hình muốn mở. (Ví dụ: Đang ở `LoginActivity`, yêu cầu mở `HomeActivity`).
    * **Implicit Intent (Không tường minh):** Không chỉ đích danh ai, chỉ nói ra "Hành động" mong muốn. (Ví dụ: Gửi Intent "Tôi muốn mở 1 đường link", Android sẽ tự động hiển thị danh sách các app Trình duyệt như Chrome, Cốc Cốc cho bạn chọn).

---

### 1.3. Khai báo bắt buộc (Manifest)
Để hệ điều hành Android biết và cho phép sử dụng các thành phần trên, bạn **bắt buộc** phải khai báo chúng một cách minh bạch vào file "sổ hộ khẩu" của ứng dụng có tên là `AndroidManifest.xml`.

**Cú pháp thẻ XML tương ứng:**
* Thẻ `<activity>` dùng cho Activities.
* Thẻ `<service>` dùng cho Services.
* Thẻ `<receiver>` dùng cho Broadcast Receivers.
* Thẻ `<provider>` dùng cho Content Providers.
* *Lưu ý:* Intent không cần khai báo thẻ riêng, mà thường được lồng vào bên trong các thẻ trên thông qua thẻ con `<intent-filter>`.

## 2. Hệ thống Bố cục (ViewGroup) & Tối ưu hóa UI

### 2.1. LinearLayout (Bố cục tuyến tính)

#### 1. Bản chất (Định nghĩa)
**LinearLayout** là một `ViewGroup` sắp xếp tất cả các thành phần View con bên trong nó tuân theo một hướng duy nhất: hoặc là **nối tiếp nhau theo chiều dọc** (từ trên xuống dưới), hoặc là **nối tiếp nhau theo chiều ngang** (từ trái sang phải).

Điểm đáng giá nhất của LinearLayout khiến nó vẫn được ứng dụng rộng rãi hiện nay chính là khả năng phân chia không gian màn hình theo tỷ lệ toán học (ví dụ: chia đôi màn hình 50-50, hoặc chia 30-70) thông qua thuộc tính **Trọng số (Weight)**.

#### 2. Các thuộc tính cốt lõi cần nắm vững

**A. Thuộc tính định hướng (Bắt buộc)**
* `android:orientation`: Quyết định hướng xếp hàng của các View con.
  * Giá trị `vertical`: Xếp dọc.
  * Giá trị `horizontal`: Xếp ngang.

**B. Nhóm thuộc tính chia tỷ lệ (Phân bổ không gian)**
* `android:layout_weight` (Trọng số): Nhận giá trị là số (thường là số nguyên như 1, 2, 3...). Nó xác định mức độ "chiếm lĩnh" phần không gian còn trống của một View so với các View anh em của nó.
  * *Quy tắc kỹ thuật nghiêm ngặt:* Khi chia tỷ lệ theo chiều ngang (`horizontal`), bạn **bắt buộc** phải thiết lập `android:layout_width="0dp"`. Ngược lại, nếu chia theo chiều dọc (`vertical`), phải thiết lập `android:layout_height="0dp"`.
* `android:weightSum`: Định nghĩa tổng trọng số của toàn bộ bố cục. (Ví dụ: Bạn thiết lập `weightSum="10"` ở thẻ cha, và gán một thẻ con có `layout_weight="5"`, thẻ con đó sẽ luôn chiếm đúng 50% không gian dù bên cạnh nó có View khác hay không).

**C. Phân biệt Gravity và Layout_Gravity (Câu hỏi phỏng vấn trọng tâm)**
* `android:gravity`: Tác động lên **nội dung (ruột)** của View đó.
  * *Ví dụ:* Căn giữa dòng chữ bên trong một nút bấm (`Button`).
* `android:layout_gravity`: Tác động lên **bản thân cái View đó** dựa trên không gian của Layout cha.
  * *Ví dụ:* Đẩy nguyên cả nút bấm đó ra chính giữa màn hình.

#### 3. Ưu điểm và Nhược điểm
* **Ưu điểm:** Cú pháp cực kỳ đơn giản, trực quan. Rất hoàn hảo để tạo các danh sách một chiều hoặc các khối giao diện cần chia tỷ lệ không gian chính xác.
* **Nhược điểm (Vấn đề hiệu năng):** Nếu lạm dụng LinearLayout lồng nhau (Nested LinearLayouts) — ví dụ: dùng Layout dọc bọc Layout ngang, rồi Layout ngang lại bọc Layout dọc khác — sẽ làm tăng độ sâu của "Cây phân cấp View" (View Hierarchy). Việc hệ thống phải tính toán tỷ lệ (`weight`) nhiều lần ở các tầng khác nhau sẽ làm giảm tốc độ kết xuất (rendering) giao diện của thiết bị.

#### 4. Ví dụ thực chiến
**Yêu cầu:** Tạo một thanh công cụ ở dưới cùng màn hình (Bottom Bar) gồm 2 nút bấm: "Từ chối" và "Đồng ý", chia đôi màn hình mỗi nút chiếm đúng 50% diện tích, bất kể kích thước thiết bị.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="[http://schemas.android.com/apk/res/android](http://schemas.android.com/apk/res/android)"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp">

    <Button
        android:id="@+id/btnDecline"
        android:layout_width="0dp"          android:layout_height="wrap_content"
        android:layout_weight="1"           android:layout_marginEnd="8dp"      android:text="Từ chối"
        android:backgroundTint="#F44336" /> <Button
        android:id="@+id/btnAccept"
        android:layout_width="0dp"          android:layout_height="wrap_content"
        android:layout_weight="1"           android:layout_marginStart="8dp"    android:text="Đồng ý"
        android:backgroundTint="#4CAF50" /> </LinearLayout>
```

### 2.2. ConstraintLayout (Bố cục ràng buộc)

#### 1. Bản chất (Định nghĩa)
**ConstraintLayout** là một `ViewGroup` cho phép quản lý vị trí và kích thước của các View con dựa trên cơ chế **"Neo" (Constraints)**. Thay vì xếp hàng tuyến tính như LinearLayout, hệ thống sẽ gắn các cạnh của View này vào cạnh của View khác hoặc vào khung của cha (`parent`).

Mục đích thiết kế cốt lõi của ConstraintLayout là giải quyết bài toán **"Layout phẳng" (Flat View Hierarchy)**. Nó cho phép xây dựng những giao diện cực kỳ phức tạp trên một tầng Layout duy nhất. Điều này giúp loại bỏ tình trạng "Nested Layout" (lồng ghép nhiều tầng), từ đó tối ưu hóa vượt bậc tốc độ kết xuất (rendering) giao diện của hệ điều hành.

#### 2. Các thuộc tính cốt lõi (Hệ thống Neo)
Hầu hết các thuộc tính của ConstraintLayout đều bắt đầu bằng tiền tố `app:layout_constraint...`.

**A. Nhóm neo vị trí cơ bản (Positioning)**
Để một View xác định được vị trí, nó cần ít nhất 1 điểm neo theo chiều ngang (Trái/Phải) và 1 điểm neo theo chiều dọc (Trên/Dưới).
* `app:layout_constraintTop_toTopOf`: Neo đỉnh của View này vào đỉnh của View kia.
* `app:layout_constraintBottom_toBottomOf`: Neo đáy của View này vào đáy của View kia.
* `app:layout_constraintStart_toEndOf`: Neo cạnh trái của View này vào cạnh phải của View kia (thường dùng để xếp các thành phần nằm cạnh nhau).
* `app:layout_constraintEnd_toEndOf`: Neo cạnh phải của View này vào cạnh phải của cha (`parent`).

**B. Nhóm kéo lệch (Bias)**
Khi một View được neo vào cả hai bên (trái và phải, hoặc trên và dưới), nó sẽ tự động nằm ở chính giữa. Để điều chỉnh nó lệch sang một bên mà không cần hardcode bằng Margin, ta sử dụng Bias:
* `app:layout_constraintHorizontal_bias`: Căn lệch theo chiều ngang. Nhận giá trị thập phân từ `0.0` (sát mép trái) đến `1.0` (sát mép phải). Mặc định là `0.5` (chính giữa).
* `app:layout_constraintVertical_bias`: Căn lệch theo chiều dọc.

**C. Nhóm tỉ lệ và kích thước (Dimension)**
* **`0dp` (Match Constraint):** Trong ConstraintLayout, không sử dụng `match_parent`. Thay vào đó, ta thiết lập kích thước là `0dp` để chỉ định View: "Hãy giãn nở tối đa dựa trên các điểm đã neo".
* `app:layout_constraintDimensionRatio`: Ép tỉ lệ khung hình (Ví dụ: `"16:9"` cho màn hình video hoặc `"1:1"` cho ảnh Avatar vuông). Yêu cầu bắt buộc: ít nhất 1 chiều (width hoặc height) phải được set là `0dp`.

#### 3. Các công cụ hỗ trợ nâng cao
* **Guidelines (Đường dóng):** Là những đường kẻ phụ trợ vô hình đối với người dùng cuối. Giúp tạo ra các mốc (ví dụ: mốc 50% màn hình) để neo các View khác vào đó một cách chuẩn xác.
* **Chains (Chuỗi liên kết):** Khi các View được neo vào nhau một cách xoay vòng (A neo vào B, B neo ngược lại A), chúng tạo thành một "Chuỗi". Cung cấp khả năng phân bổ không gian linh hoạt (giãn đều, co cụm) tương tự như thuộc tính `weight` của LinearLayout.

#### 4. Ví dụ thực chiến
**Yêu cầu:** Thiết kế một thẻ người dùng cơ bản: Đặt một tấm ảnh Avatar bên trái, và tên người dùng nằm bên phải tấm ảnh, tự động căn giữa theo chiều dọc của tấm ảnh đó.

```xml
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="[http://schemas.android.com/apk/res/android](http://schemas.android.com/apk/res/android)"
    xmlns:app="[http://schemas.android.com/apk/res-auto](http://schemas.android.com/apk/res-auto)"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/imgAvatar"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:src="@drawable/avatar_sample"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_margin="16dp" />

    <TextView
        android:id="@+id/tvUserName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Nguyễn Quang Minh"
        android:textSize="18sp"
        android:textStyle="bold"
        android:layout_marginStart="12dp"
        android:layout_marginEnd="16dp"
        
        /* Cụm ràng buộc cốt lõi: */
        app:layout_constraintStart_toEndOf="@+id/imgAvatar" app:layout_constraintEnd_toEndOf="parent"          app:layout_constraintTop_toTopOf="@+id/imgAvatar"    app:layout_constraintBottom_toBottomOf="@+id/imgAvatar" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 2.3. FrameLayout (Bố cục xếp lớp)

#### 1. Bản chất (Định nghĩa)
**FrameLayout** là `ViewGroup` có cơ chế hoạt động đơn giản và tốn ít tài nguyên nhất trong Android. Nó được thiết kế để chứa một hoặc nhiều View con và xếp chồng chúng lên nhau theo **trục Z (chiều sâu)**. 

Quy luật hiển thị của FrameLayout tuân theo nguyên lý Z-index tự nhiên: Thẻ XML nào được hệ thống biên dịch **sau** (nằm ở phía dưới trong file code) sẽ được vẽ đồ họa **đè lên trên mặt** các thẻ được khai báo trước nó.

#### 2. Thuộc tính cốt lõi
FrameLayout không hỗ trợ các tính năng dàn trang phức tạp như chia tỷ lệ (`weight`) hay neo điểm (`constraint`). Công cụ duy nhất để kiểm soát vị trí của View con bên trong nó là:
* `android:layout_gravity`: Trọng lực cục bộ. Dùng để "hút" View con trôi về các góc, các mép biên hoặc vị trí trung tâm của khoảng không gian FrameLayout.
  * *Các giá trị kết hợp phổ biến:* `center` (vào chính giữa), `bottom|end` (hút xuống góc dưới cùng bên phải), `top|start` (góc trên cùng bên trái). Sử dụng toán tử bitwise `|` để kết hợp các hướng.

#### 3. Ứng dụng chuyên sâu (Trọng tâm phỏng vấn)
Trong thực tế, không ai dùng FrameLayout để dàn các màn hình có nhiều nút bấm hay văn bản. Tuy nhiên, nó là "vị cứu tinh" độc quyền cho 2 trường hợp thiết kế kiến trúc sau:

* **Làm Fragment Container (Khung chứa rỗng):** Trong kiến trúc ứng dụng hiện đại (Single-Activity Architecture), lập trình viên sẽ khai báo một FrameLayout (bên trong rỗng không có gì) làm bộ khung. Sau đó, sử dụng mã Kotlin (thông qua `FragmentManager` hoặc `Navigation Component`) để linh hoạt nhúng, thay thế hoặc gỡ bỏ các màn hình `Fragment` vào cái khung đó.
* **Tạo hiệu ứng lớp phủ (Overlay Layout):** Hoàn hảo để thiết kế các thành phần đồ họa trôi nổi độc lập. Ví dụ: 
  * Hiển thị một vòng xoay tải dữ liệu (`ProgressBar`) đè lên chính giữa màn hình danh sách khi đang gọi API.
  * Đặt một huy hiệu chấm đỏ (Badge) thông báo đè lên góc phải của một biểu tượng (Icon) giỏ hàng.

#### 4. Ví dụ thực chiến
**Yêu cầu:** Hiển thị một bức ảnh bìa (Cover), và đặt một biểu tượng vòng xoay tải dữ liệu (Loading) nằm đè lên vị trí chính giữa của bức ảnh đó.

```xml
<FrameLayout xmlns:android="[http://schemas.android.com/apk/res/android](http://schemas.android.com/apk/res/android)"
    android:layout_width="match_parent"
    android:layout_height="250dp">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"
        android:src="@drawable/img_cover_sample" />

    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        
        /* Cú pháp cốt lõi để đưa View ra giữa khung chứa: */
        android:layout_gravity="center" 
        android:indeterminateTint="#FFFFFF" />

</FrameLayout>
```

### 2.4. ScrollView / NestedScrollView (Bố cục cuộn)

#### 1. Bản chất (Định nghĩa)
**ScrollView** (và biến thể hiện đại của nó là **NestedScrollView**) là một `ViewGroup` đặc biệt. Nhiệm vụ duy nhất của nó là mở rộng không gian hiển thị (Viewport), cho phép người dùng thực hiện thao tác vuốt dọc (vertical scroll) để xem các nội dung có kích thước vượt quá chiều cao vật lý của màn hình thiết bị.

* **Quy tắc kỹ thuật BẤT DI BẤT DỊCH (Trọng tâm):** Bất kỳ một Layout cuộn nào cũng **chỉ được phép chứa duy nhất một (1) View hoặc ViewGroup con trực tiếp**. 
  * *Cách làm chuẩn:* Đặt một `ScrollView` làm khung ngoài cùng, bên trong nó bọc một `LinearLayout` (hoặc `ConstraintLayout`), và sau đó bạn có thể nhét bao nhiêu thẻ con vào trong `LinearLayout` đó tùy thích. Nếu bạn cố tình đặt 2 thẻ ngang hàng nhau ngay bên trong ScrollView, ứng dụng sẽ bị Crash (văng) ngay lập tức.

#### 2. Các thuộc tính cốt lõi cần tối ưu
* **`android:fillViewport`** (`true` | `false`): Đây là thuộc tính quan trọng nhất để sửa lỗi giao diện. Khi nội dung bên trong quá ngắn (chưa đủ dài để phải cuộn), giao diện thường bị co cụm lại ở sát mép trên màn hình. Đặt giá trị `true` sẽ ép cái ViewGroup con duy nhất bên trong phải giãn nở chiều cao (`match_parent`) để lấp đầy toàn bộ không gian trống của màn hình.
* **`android:scrollbars`** (`none` | `vertical`): Hiển thị hoặc ẩn đi thanh cuộn mờ ở mép phải màn hình. Trong thiết kế UI/UX hiện đại, lập trình viên thường đặt là `none` để giao diện trông gọn gàng, liền mạch hơn.
* **`android:overScrollMode`** (`never` | `always`): Khi người dùng vuốt đến tận cùng (đỉnh hoặc đáy) của nội dung và cố tình vuốt thêm, hệ thống sẽ hiển thị một hiệu ứng đồ họa lóe sáng (vầng bán nguyệt). Đặt là `never` sẽ tắt hiệu ứng này đi, rất hữu ích khi bạn làm các giao diện tùy chỉnh cao.

#### 3. Phân biệt ScrollView và NestedScrollView (Câu hỏi phỏng vấn)
Đây là câu hỏi phân loại ứng viên để xem bạn có kinh nghiệm giải quyết "xung đột thao tác chạm" (Touch Event Conflict) hay không.

* **`ScrollView` (Truyền thống):** Xử lý cuộn cho các màn hình dài cơ bản (như một bài báo chữ, một form điền thông tin).
  * *Vấn đề:* Sẽ bị lỗi "kẹt vuốt" nếu bạn đặt một danh sách có thể cuộn khác (như `RecyclerView` hoặc bản đồ `Google Maps`) vào bên trong nó. Hệ điều hành không biết ngón tay của bạn đang muốn cuộn cái ScrollView ở ngoài hay cuộn cái RecyclerView ở trong.
* **`NestedScrollView` (Hiện đại - Chuẩn mực):** Là thành phần thuộc thư viện AndroidX. Nó được tích hợp cơ chế "Nested Scrolling" thông minh, cho phép điều phối các sự kiện chạm mượt mà giữa Layout cha và Layout con.
  * *Giải pháp:* Khi có bài toán **"Cuộn lồng trong Cuộn"**, bắt buộc phải sử dụng `NestedScrollView` làm vòng bọc ngoài cùng. Nó sẽ cho phép người dùng cuộn mượt mà cái `RecyclerView` bên trong cho đến hết danh sách, rồi mới tiếp tục cuộn màn hình tổng thể ở bên ngoài.

#### 4. Ví dụ thực chiến
**Yêu cầu:** Thiết kế một màn hình "Form Đăng Ký" gồm hàng chục ô nhập liệu (EditText). Bắt buộc phải cuộn được và đảm bảo khi nội dung ít, các ô nhập liệu vẫn được căn đều trên màn hình chứ không bị dúm lại ở góc trên.

```xml
<androidx.core.widget.NestedScrollView 
    xmlns:android="[http://schemas.android.com/apk/res/android](http://schemas.android.com/apk/res/android)"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    
    /* Thuộc tính sinh tử: Ép con bên trong giãn đầy màn hình */
    android:fillViewport="true" 
    
    /* Làm đẹp giao diện: Ẩn thanh cuộn và tắt hiệu ứng lóe sáng */
    android:scrollbars="none"
    android:overScrollMode="never">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="20dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Tạo tài khoản"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_marginBottom="30dp"/>

        <EditText android:layout_width="match_parent" android:layout_height="wrap_content" android:hint="Họ và tên" />
        <EditText android:layout_width="match_parent" android:layout_height="wrap_content" android:hint="Email" />
        <EditText android:layout_width="match_parent" android:layout_height="wrap_content" android:hint="Mật khẩu" />
        <View 
            android:layout_width="match_parent" 
            android:layout_height="1000dp" 
            android:background="#FAFAFA"/> <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Đăng ký ngay" />

    </LinearLayout>
</androidx.core.widget.NestedScrollView>
```

## 3. Tối ưu thao tác với XML (ViewBinding)

#### 1. Bản chất (Định nghĩa)
**ViewBinding** là một tính năng thuộc bộ công cụ Android Jetpack, được sinh ra để thay thế hoàn toàn hàm `findViewById()` truyền thống. Khi được kích hoạt, hệ thống sẽ tự động quét các file giao diện (`.xml`) và sinh ra (auto-generate) các lớp liên kết (Binding Classes) tương ứng ngay trong lúc biên dịch (Compile time).

#### 2. Lý do BẮT BUỘC phải sử dụng (Trọng tâm phỏng vấn)
Sử dụng `findViewById()` tiềm ẩn hai rủi ro "chết người" khiến ứng dụng bị văng (Crash) ở thời gian thực thi (Runtime). ViewBinding giải quyết triệt để cả hai vấn đề này:
* **Null-safe (An toàn với Null):** ViewBinding tạo ra tham chiếu trực tiếp đến các View dựa trên ID. Bạn không thể gọi một ID không tồn tại hoặc gọi nhầm ID của một Layout khác, loại bỏ hoàn toàn lỗi `NullPointerException`.
* **Type-safe (An toàn kiểu dữ liệu):** Các trường (fields) trong class Binding được định nghĩa sẵn đúng với kiểu View trong XML (Ví dụ: Thẻ `<TextView>` sẽ được map chuẩn xác thành class `TextView` trong Kotlin). Bạn không cần ép kiểu thủ công, loại bỏ lỗi `ClassCastException`.

#### 3. Cấu hình
**Bước 1: Kích hoạt trong file `build.gradle` (cấp độ App)**
```groovy
android {
    ...
    buildFeatures {
        viewBinding = true
    }
}
```

**Bước 2: Sử dụng trong Activity**
```groovy
class MainActivity : AppCompatActivity() {
    // 1. Khai báo biến binding (Tên class tự động sinh ra: activity_main.xml -> ActivityMainBinding)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 2. Thổi phồng (inflate) giao diện và gán vào biến binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        
        // 3. Đưa cái "gốc" (root) của layout lên màn hình
        setContentView(binding.root)

        // 4. Truy xuất trực tiếp các View thông qua ID cực kỳ an toàn
        binding.tvTitle.text = "Đăng nhập hệ thống"
        binding.btnLogin.setOnClickListener {
            // Xử lý sự kiện click
        }
    }
```

## 4. Hệ thống Phản hồi người dùng (User Feedback)

Trong thiết kế UX/UI, khi một sự kiện nền xảy ra (lưu dữ liệu, mất kết nối mạng, lỗi hệ thống), ứng dụng có trách nhiệm phải thông báo trạng thái đó cho người dùng. Android cung cấp 4 cơ chế phản hồi với mức độ can thiệp từ nhẹ nhàng (Non-blocking) đến chặn luồng (Blocking):

### 4.1. Toast (Thông báo thoáng qua)
* **Bản chất:** Là một bong bóng văn bản nhỏ nổi lên ở góc dưới màn hình, tự động biến mất sau một khoảng thời gian định trước (`LENGTH_SHORT` hoặc `LENGTH_LONG`). Nó **không chặn** luồng tương tác hiện tại của người dùng.
* **Mục đích sử dụng:** Chủ yếu dùng để **Debug mã nguồn** cho lập trình viên, hoặc thông báo các lỗi vặt không mang tính quyết định ("Bấm Back lần nữa để thoát"). 
* **Lưu ý thiết kế:** Trong các ứng dụng thương mại hiện đại, Toast đang dần bị hạn chế do không thể tùy biến giao diện linh hoạt và thiếu tính tương tác.
* **Cú pháp:**
```kotlin
  Toast.makeText(this, "Đang tải dữ liệu...", Toast.LENGTH_SHORT).show()
}
```

### 4.2. Snackbar (Chuẩn mực Material Design)
* **Bản chất:** Là bản nâng cấp trực tiếp của Toast, hiển thị một thanh thông báo từ cạnh đáy màn hình lên, tuân thủ nghiêm ngặt ngôn ngữ thiết kế Material Design của Google.
* **Ưu điểm cốt lõi (Trọng tâm phỏng vấn):**
  1. **Tính tương tác (Action):** Cho phép nhúng một nút hành động để người dùng "chuộc lỗi" (Ví dụ điển hình: Hiển thị Snackbar "Đã xóa email" kèm theo nút "Hoàn tác - Undo").
  2. **Trải nghiệm người dùng (Swipe-to-dismiss):** Người dùng không cần chờ nó tự tắt mà có thể chủ động vuốt ngang để loại bỏ thông báo ngay lập tức.
* **Cú pháp:**
```kotlin
  Snackbar.make(binding.root, "Đã xóa tệp tin thành công", Snackbar.LENGTH_LONG)
      .setAction("Hoàn tác") {
          // Logic khôi phục lại tệp tin vừa xóa
      }.show()
```

### 4.3. AlertDialog (Hộp thoại cảnh báo - Chặn luồng)
* **Bản chất:** Là một cửa sổ (Modal) bật lên ở trung tâm màn hình, tự động làm mờ phông nền phía sau và **chặn toàn bộ thao tác** (Focus-stealing) của người dùng đối với Activity hiện tại cho đến khi họ đưa ra quyết định.
* **Quy tắc sử dụng:** Chỉ được phép sử dụng cho những tác vụ **mang tính phá hủy, không thể vãn hồi hoặc cực kỳ quan trọng** (Ví dụ: Xác nhận Xóa vĩnh viễn tài khoản, Thanh toán đơn hàng, Đăng xuất).
* **Cấu trúc:** Gồm Tiêu đề (Title), Nội dung chi tiết (Message) và tối đa 3 nút bấm (Positive - Khẳng định, Negative - Phủ định, Neutral - Trung lập).
* **Cú pháp:**
  ```kotlin
  AlertDialog.Builder(this)
      .setTitle("Cảnh báo bảo mật")
      .setMessage("Bạn có chắc chắn muốn xóa toàn bộ dữ liệu hệ thống không?")
      .setPositiveButton("Xóa ngay") { dialog, _ -> 
          // Thực thi lệnh xóa
      }
      .setNegativeButton("Hủy bỏ") { dialog, _ -> 
          dialog.dismiss() // Đóng hộp thoại an toàn
      }
      .setCancelable(false) // Buộc người dùng phải chọn nút, không cho bấm ra viền ngoài để tắt
      .show()
  ```

  ### 4.4. BottomSheetDialog (Cửa sổ trượt từ đáy - UX Hiện đại)
* **Bản chất:** Một cửa sổ tương tác trượt mượt mà từ dưới mép đáy màn hình lên. Khác với sự can thiệp thô bạo của `AlertDialog`, `BottomSheetDialog` mang lại cảm giác chuyển đổi bối cảnh nhẹ nhàng hơn rất nhiều.
* **Ưu điểm UX (Trọng tâm thiết kế hiện đại):** * Nằm hoàn toàn trong vùng **"Thumb-friendly area" (Vùng thân thiện với ngón tay cái)**, cực kỳ tối ưu cho thao tác bằng một tay trên các dòng smartphone tỷ lệ màn hình dài (18:9, 20:9) hiện nay.
  * Hỗ trợ cử chỉ vuốt (Swipe down) để đóng một cách tự nhiên.
* **Ứng dụng thực tế:** Thường được sử dụng để thay thế cho các Menu dạng Dropdown (thả xuống) cũ kỹ, hoặc chứa một danh sách tùy chọn nâng cao (Ví dụ: Menu Chia sẻ lên mạng xã hội, Bảng chọn màu sắc/kích cỡ sản phẩm trên các app E-commerce).
* **Cú pháp / Ví dụ thực chiến:**
  Để tạo một `BottomSheetDialog`, bạn thường tự thiết kế một file XML riêng (ví dụ: `layout_bottom_sheet_share.xml`), sau đó dùng code Kotlin để nạp nó lên và bắt sự kiện:
  
```kotlin
  // 1. Khởi tạo đối tượng BottomSheetDialog (Context là 'this' nếu gọi trong Activity)
  val bottomSheetDialog = BottomSheetDialog(this)
  
  // 2. Nạp giao diện XML tự thiết kế vào Dialog
  val view = layoutInflater.inflate(R.layout.layout_bottom_sheet_share, null)
  bottomSheetDialog.setContentView(view)
  
  // 3. Xử lý sự kiện click cho các thành phần bên trong BottomSheet
  val btnShareFacebook = view.findViewById<LinearLayout>(R.id.btnShareFacebook)
  btnShareFacebook.setOnClickListener {
      // Xử lý logic chia sẻ
      Toast.makeText(this, "Đang chia sẻ...", Toast.LENGTH_SHORT).show()
      
      // Đóng BottomSheet sau khi người dùng đã chọn xong
      bottomSheetDialog.dismiss() 
  }
  
  // 4. Hiển thị BottomSheet lên màn hình
  bottomSheetDialog.show()
```

## 2. Hệ điều hành & Thành phần Cốt lõi

### 2.1. Context (Ngữ cảnh)
* **Bản chất:** `Context` là chiếc thẻ định danh (interface) cung cấp thông tin về môi trường hiện tại của ứng dụng. Nó là "giấy thông hành" bắt buộc để truy cập vào tài nguyên hệ thống (như String, Drawable), khởi chạy Activity, hoặc thao tác với Database.
* **Context cho phép bạn**
  - Load ảnh, âm thanh, string từ res
  - Hiện Toast, Dialog, Snackbar
  - Mở Activity mới
  - Truy cập SharedPreferences, Database
  - Gọi các Service hệ thống (GPS, Camera...)
* **Câu hỏi phỏng vấn: Phân biệt các loại Context & Lỗi Memory Leak:**
  1. **Activity Context (`this`):** * *Vòng đời:* Gắn liền với màn hình (Activity) hiện tại. Khi màn hình bị hủy, Context này cũng bị hủy theo.
     * *Khi nào dùng:* Chỉ dùng cho các thao tác liên quan trực tiếp đến giao diện (UI) như: Hiển thị `Toast`, show `AlertDialog`, hoặc `LayoutInflater`.
     * *Hiểm họa rò rỉ bộ nhớ (Memory Leak):* Nếu bạn truyền Activity Context vào một tác vụ chạy ngầm (Background Thread) mất 10 phút mới xong, mà người dùng lại thoát màn hình ở phút thứ 2, thì màn hình đó **không thể bị thu hồi bộ nhớ (Garbage Collector không thể xóa)** vì tác vụ ngầm vẫn đang giữ Context của nó.
  2. **Application Context (`applicationContext`):**
     * *Vòng đời:* Gắn liền với toàn bộ ứng dụng. Ứng dụng còn sống thì nó còn tồn tại.
     * *Khi nào dùng:* Dùng để khởi tạo các đối tượng dùng chung (Singleton) hoặc các thư viện hoạt động xuyên suốt app như: Room Database, Retrofit (gọi API), Shared Preferences.
* **Tại sao phải phân biệt Activity Context và Application Context?**
* **Activity Context (Chứa Window Token):**
* *Lý thuyết:* Mỗi Activity khi sinh ra sẽ được hệ điều hành cấp một cái vé gọi là `Window Token` để được phép vẽ giao diện lên màn hình. `Toast`, `Dialog` hay `LayoutInflator` đều yêu cầu phải có `Window Token` này mới vẽ được.
* *Ví dụ ứng dụng:* Nếu bạn cố tình dùng `Application Context` để gọi hàm `show()` một cái `AlertDialog`, ứng dụng sẽ văng ngay lập tức với lỗi `BadTokenException` vì Application Context không hề có `Window Token` để vẽ UI.
* **Application Context (Tuổi thọ toàn cục):**
* *Lý thuyết:* Trực thuộc tiến trình (Process) của ứng dụng. 
* *Ví dụ ứng dụng:* Bạn đang tải một file 1GB chạy ngầm (Background Service). Tiến trình tải này cần một Context để ghi file vào bộ nhớ máy. Bắt buộc phải truyền `Application Context`, vì nếu truyền `Activity Context`, khi người dùng thoát màn hình đó, Context bị thu hồi, tiến trình tải file sẽ bị lỗi và gây ra Memory Leak.

---

### 2.2. Intent & Truyền dữ liệu (Payload)
`Intent` không chỉ dùng để chuyển màn hình, mà còn đóng vai trò là "người đưa thư" mang theo dữ liệu (gọi là Payload hoặc Extras).

* **Truyền dữ liệu nguyên thủy (Primitive Types):**
  * Hỗ trợ các kiểu cơ bản: `String`, `Int`, `Boolean`, `Float`,...
  * *Cú pháp truyền:* `intent.putExtra("KEY_NAME", "Nguyễn Quang Minh")`
  * *Cú pháp nhận:* `intent.getStringExtra("KEY_NAME")`
* **Truyền đối tượng (Object - Điểm phân loại ứng viên):**
  * Không thể ném trực tiếp một Object (ví dụ: class `User`) vào Intent. Nó phải được "đóng gói" lại.
  * **Serializable (Java cũ):** Đã lỗi thời. Nó dùng cơ chế Reflection để quét các biến trong class, quá trình này tạo ra rác bộ nhớ (Garbage) cực nhiều và làm tốc độ chuyển màn hình bị chậm.
  * **Parcelable (Chuẩn mực Android):** Bắt buộc phải dùng. Đây là cơ chế đóng gói được Google tối ưu hóa sâu ở tầng C/C++ cho Android, tốc độ đọc/ghi cực kỳ nhanh.
  * **Kotlin `@Parcelize`:** Ngày xưa viết class Parcelable mất cả trăm dòng code, hiện tại Kotlin hỗ trợ annotation `@Parcelize` giúp thu gọn mọi thứ thành 1 dòng:
    ```kotlin
    @Parcelize
    data class User(val id: Int, val name: String) : Parcelable
    
    // Truyền đi: intent.putExtra("USER_DATA", userObj)
    // Nhận về (Android 13+): intent.getParcelableExtra("USER_DATA", User::class.java)
    ```

---

### 2.3. Lifecycle (Vòng đời) & Tương tác API
Đây là kiến thức sống còn để app không bị Crash (văng) khi làm việc với Backend (gọi API).

* **Mô hình Vòng đời cơ bản:**
  * **`onCreate()`**: Hàm duy nhất chạy 1 lần. Chuyên dùng để khởi tạo `ViewBinding`, thiết lập Adapter cho RecyclerView. Đây cũng là nơi lý tưởng để **bắt đầu gọi API lấy dữ liệu lần đầu tiên**.
  * **`onStart()`**: Giao diện hiển thị, nhưng chưa thể bấm chạm.
  * **`onResume()`**: Trạng thái Foreground. App đang tương tác trực tiếp với người dùng.
  * **`onPause()`**: Màn hình bị che khuất một phần (Ví dụ: Dialog hiển thị lên). **Bắt buộc:** Tạm dừng các UI update hao tài nguyên (hoạt ảnh, video).
  * **`onStop()`**: Màn hình bị che lấp 100% (Ví dụ: Bấm phím Home).
  * **`onDestroy()`**: Màn hình bị hệ thống tiêu diệt. 

* **Quy tắc khi gọi API (Tránh văng app):**
  * Giả sử bạn ở `onCreate()`, bạn gọi một API lấy danh sách User mất 5 giây. Nhưng ở giây thứ 2, người dùng bấm nút Back để thoát màn hình (Activity bị đưa vào `onDestroy()`).
  * Ở giây thứ 5, API trả data về, code của bạn chạy lệnh `binding.textView.text = data`. 
  * **Hậu quả:** Ứng dụng Crash ngay lập tức (NullPointerException / View is dead) vì nó cố gắng cập nhật giao diện trên một Activity đã chết.
  * **Cách giải quyết:** Bất kỳ thao tác gọi API hoặc Background Thread nào cũng **bắt buộc phải bị hủy (Cancel)** trong hàm `onDestroy()`. (Hiện nay, nếu dùng Kotlin Coroutines với `lifecycleScope` hoặc `viewModelScope`, hệ thống sẽ tự động làm việc hủy này cho bạn).
 
### 2.4. Xử Lý Sự Kiện (Event Handling) - Tư duy Thực chiến & Tối ưu Hiệu năng

Thay vì chỉ ghi nhớ cú pháp lệnh, điều làm nên sự khác biệt của một lập trình viên giỏi là khả năng kiểm soát **dòng chảy (luồng) của sự kiện** và ngăn chặn triệt để các rủi ro về UX (Trải nghiệm người dùng) cũng như Hiệu năng.

#### 2.4.1. Bản chất hệ thống: Cơ chế Tiêu thụ sự kiện (Event Consumption)
* **Câu hỏi phỏng vấn xoáy:** Tại sao các hàm như `setOnLongClickListener` (Nhấn giữ) hay `setOnTouchListener` (Chạm vuốt) lại bắt buộc phải `return true` hoặc `false` ở cuối hàm?
* **Bản chất kiến trúc (Under the hood):** Android sử dụng cơ chế truyền sự kiện theo dạng chuỗi. Khi người dùng chạm vào màn hình, sự kiện sẽ đi từ lớp vỏ ngoài cùng (Activity) -> Xuyên qua Layout cha -> Đến Nút bấm con.
  * Nếu bạn `return true`: Hệ thống hiểu là *"Tôi đã xử lý trọn vẹn sự kiện này rồi, hãy ngắt luồng đi"*. 
  * Nếu bạn `return false`: Hệ thống hiểu là *"Tôi mới chỉ xem qua thôi, chưa xử lý triệt để"*. Sự kiện này sẽ tiếp tục **trào ngược (bubble up)**. Hệ quả: Nút bấm của bạn sẽ vừa chạy code của hàm Nhấn Giữ, lại vừa chạy luôn cả hàm Nhấn Thường (`onClick`), gây ra lỗi logic nghiêm trọng.

#### 2.4.2 Click Event — Sự Kiện Nhấn

**Cách 1: Dùng lambda (khuyến nghị)**

```kotlin
// Trong Activity
btnLogin.setOnClickListener {
    // Xử lý khi người dùng nhấn nút
    Toast.makeText(this, "Đã nhấn Đăng Nhập!", Toast.LENGTH_SHORT).show()
}
```

**Cách 2: Implement interface**

```kotlin
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnLogin    -> handleLogin()
            R.id.btnRegister -> handleRegister()
        }
    }
}
```

**Cách 3: Khai báo trong XML (ít dùng)**

```xml
<Button
    android:onClick="handleLogin"
    ... />
```

```kotlin
// Trong Activity — tên hàm phải khớp với XML
fun handleLogin(view: View) {
    // ...
}
```

#### 2.4.3 Long Click Event — Nhấn Giữ

```kotlin
btnDelete.setOnLongClickListener {
    Toast.makeText(this, "Nhấn giữ để xoá", Toast.LENGTH_SHORT).show()
    true  // true = đã xử lý, không tiếp tục truyền sự kiện
}
```

#### 2.4.4 Touch Event — Sự Kiện Chạm

```kotlin
view.setOnTouchListener { v, event ->
    when (event.action) {
        MotionEvent.ACTION_DOWN -> { /* Bắt đầu chạm */ }
        MotionEvent.ACTION_MOVE -> { /* Đang di chuyển */ }
        MotionEvent.ACTION_UP   -> { /* Nhấc tay lên */ }
    }
    true
}
```

#### 2.4.5 Text Change — Theo Dõi Thay Đổi Text

```kotlin
etSearch.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Trước khi text thay đổi
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Trong khi text đang thay đổi — dùng nhiều nhất
        performSearch(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
        // Sau khi text đã thay đổi
    }
})
```

#### 2.4.6 Focus Change — Thay Đổi Focus

```kotlin
etEmail.setOnFocusChangeListener { view, hasFocus ->
    if (hasFocus) {
        tilEmail.error = null  // Xoá lỗi khi người dùng bắt đầu nhập
    } else {
        // Validate khi rời focus
        if (etEmail.text.isNullOrEmpty()) {
            tilEmail.error = "Email không được trống"
        }
    }
}
```
---

### 2.5. Giao tiếp 2 chiều (ActivityResultLauncher)
**A. Tại sao Google "khai tử" `startActivityForResult` cũ?**
* *Lý thuyết điểm yếu cũ:* Hệ thống cũ gắn chặt logic lắng nghe kết quả vào hàm `onActivityResult()` của Activity. Nếu màn hình A gọi màn hình B (ví dụ mở Camera), nhưng điện thoại yếu RAM, HĐH tạm thời giết màn hình A. Khi B chụp xong quay lại, màn hình A được phục hồi nhưng kết quả có nguy cơ bị mất hoặc xử lý sai luồng.
* *Bản chất hệ thống mới:* `ActivityResultLauncher` sử dụng cơ chế **Registry (Đăng ký)**. Bạn đăng ký một hợp đồng (Contract) với hệ thống từ trước. Cho dù màn hình A có bị giết và phục hồi lại, hệ thống vẫn nhớ cái hợp đồng đó và trả kết quả về đúng chỗ.

**B. Ví dụ ứng dụng thực tế:**
* **Flow Đăng nhập Google/Facebook:** Bấm nút "Login via Google" -> Mở màn hình của Google lên -> Người dùng xác thực vân tay -> Google trả về một chuỗi Token -> `ActivityResultLauncher` bắt lấy chuỗi Token đó để đăng nhập vào app.
* **Thay đổi Avatar:** Bấm vào Avatar -> Gọi Intent mở thư viện ảnh (`ACTION_PICK`) -> Người dùng chọn ảnh -> Launcher bắt lấy đường dẫn URI của ảnh -> Đẩy lên Image/Glide để hiển thị.

---

### 2.6. Điều Hướng Ứng Dụng (Navigation Component) - Kỷ nguyên Single-Activity

Nếu `ConstraintLayout` là chuẩn mực của vẽ giao diện, thì **Jetpack Navigation Component** chính là chuẩn mực tối thượng của kiến trúc luân chuyển màn hình hiện đại. Nó sinh ra để hiện thực hóa triết lý **Single-Activity Architecture** (Ứng dụng chỉ có 1 Activity duy nhất, mọi màn hình khác đều là Fragment luân chuyển bên trong).

#### 1. Nỗi đau của quá khứ (Tại sao khai tử FragmentTransaction?)
* **Lịch sử:** Trước đây, để chuyển từ `Fragment A` sang `Fragment B`, lập trình viên phải gọi `FragmentManager`, viết lệnh `beginTransaction()`, gọi `replace()`, rồi `addToBackStack()`, và cuối cùng là `commit()`.
* **Hậu quả (Vấn nạn Crash App):** * Rất dễ dính lỗi `IllegalStateException` kinh điển nếu vô tình gọi `commit()` sau khi Activity đã lưu trạng thái (`onSaveInstanceState`).
  * Quản lý nút Back (Backstack) cực kỳ thủ công và rối rắm (Bấm back không thoát ra màn trước mà lại thoát luôn app).
  * Mã nguồn "mì ý" (Spaghetti code) vì logic chuyển màn hình rải rác khắp nơi.

#### 2. Kiến trúc 3 trụ cột của Navigation Component
Để khắc phục toàn bộ nhược điểm trên, Google đóng gói hệ thống điều hướng thành 3 thành phần cốt lõi:

* **NavGraph (`nav_graph.xml`):** Một file XML trực quan đóng vai trò là "Bản đồ tư duy". Nơi bạn kéo thả các Fragment và vẽ các mũi tên (Action) nối chúng lại với nhau. Bạn có thể nhìn lướt qua file này là hiểu ngay luồng đi của toàn bộ ứng dụng.
* **NavHostFragment:** Là một "Khung chứa rỗng" (`FragmentContainerView`) được đặt cố định bên trong `MainActivity`. Nó đóng vai trò là sân khấu để các Fragment thay phiên nhau lên diễn.
* **NavController:** Là "Bộ não điều khiển". Ở bất kỳ Fragment nào, bạn chỉ cần gọi `findNavController().navigate(R.id.action_A_to_B)`, hệ thống sẽ tự lo liệu mọi thứ (từ Animation chuyển cảnh, cho đến việc thêm màn hình A vào Backstack).

#### 3. Phép màu Tích hợp UI (BottomNavigationView & DrawerLayout)
* **Câu hỏi phỏng vấn:** *"Làm sao để kết nối cái thanh Bottom Navigation (hoặc Menu trượt Drawer) với các Fragment mà không cần dùng lệnh `switch-case` hay `if-else`?"*
* **Bí kíp Senior (Quy tắc ID đồng nhất):** Trong kiến trúc mới, bạn không cần viết bất kỳ dòng logic chuyển trang thủ công nào khi bấm vào menu. Điều kiện tiên quyết duy nhất là: **ID của `<item>` trong file `menu.xml` BẮT BUỘC PHẢI TRÙNG KHỚP 100% với ID của `<fragment>` trong file `nav_graph.xml`.**

**Thực chiến tích hợp (Chỉ với 2 dòng code trong Activity):**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // ... khởi tạo binding ...

    // 1. Tìm cái "Bộ não điều khiển" từ cái khung chứa rỗng
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController

    // 2. Gắn Thanh điều hướng dưới đáy (BottomNav) vào Bộ não
    // Phép màu nằm ở đây: Nó tự động bắt sự kiện click, tự đổi màu icon, tự chuyển Fragment tương ứng.
    binding.bottomNavigationView.setupWithNavController(navController)
}
```

#### 4. Safe Args (Truyền dữ liệu không rủi ro)
Khi chuyển từ màn hình Danh sách sang Chi tiết, ta thường phải gửi kèm một ID_Sản_Phẩm.
* Cách cũ (Dùng Bundle): bundle.putString("KEY_ID", "123"). Nhược điểm là bên nhận rất dễ gõ sai chữ "KEY_ID" gây Crash app (lỗi Runtime).
* Chuẩn mực hiện tại (Safe Args): Là một Plugin đi kèm với Navigation. Bạn khai báo biến id_san_pham trực tiếp vào bản đồ nav_graph.xml. Lúc biên dịch, hệ thống tự động sinh ra các Class code có sẵn hàm truyền/nhận chuẩn xác kiểu dữ liệu (Type-safe).

* **Ví dụ code Safe Args thực tế:**
```kotlin
// BÊN GỬI (Fragment Danh Sách): Gọi hàm tự sinh, truyền thẳng ID vào, không lo gõ sai tên KEY
val action = HomeFragmentDirections.actionHomeToDetail(productId = 123)
findNavController().navigate(action)

// BÊN NHẬN (Fragment Chi Tiết): Lấy dữ liệu an toàn 100%
private val args: DetailFragmentArgs by navArgs()

override fun onViewCreated(...) {
    val idNhanDuoc = args.productId // Kiểu Int chuẩn xác, không bị Null
    loadData(idNhanDuoc)
}
```

---
