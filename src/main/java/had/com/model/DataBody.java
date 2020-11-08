package had.com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBody {
    String content;
    String title;
    String id;
    String valueDefault;
    int typeData = 0;
    // 0: text - 1:date


    public DataBody(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public DataBody() {
    }

    public int getTypeData() {
        return typeData;
    }

    public void setTypeData(int typeData) {
        this.typeData = typeData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueDefault() {
        return valueDefault;
    }

    public void setValueDefault(String valueDefault) {
        this.valueDefault = valueDefault;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<DataBody> fakeDate() {
        List<DataBody> dataBodyList = new ArrayList<>();

        List<String> contents = Arrays.asList(
                "<<num_contract>>",
                "<<date_today>>",
                "<<a_side>>",
                "<<a_represent>>",
                "<<a_position>>",
                "<<a_address>>",
                "<<a_phone>>",
                "<<a_fax>>",
                "<<a_bank_account>>",
                "<<b_side>>",
                "<<b_represent>>",
                "<<b_position>>",
                "<<b_address>>",
                "<<b_phone>>",
                "<<b_fax>>",
                "<<b_bank_account>>",
                "<<accets_pricing>>",
                "<<time_pricing>>",
                "<<price_no_tax>>",
                "<<price_tax>>",
                "<<total_price>>",
                "<<advance_money>>",
                "<<advance_money_char>>"
        );

        List<Integer> types = Arrays.asList(
                0,
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                2,
                0,
                0,
                0,
                0,
                0
        );
        List<String> titles = Arrays.asList(
                "Số hợp đồng:",
                "Ngày lập HĐ:",
                "Bên A:",
                "Đại diện",
                "Chức vụ",
                "Địa chỉ",
                "Điện thoại",
                "Fax",
                "Tài khoản",
                "Bên B",
                "Đại diện",
                "Chức vụ",
                "Địa chỉ",
                "Điện thoại",
                "Fax",
                "Tài khoản",
                "Tài sản định giá",
                "Thời điểm định giá",
                "Giá dịch vụ thẩm định",
                "Thuế GTGT",
                "Tổng thanh toán",
                "Tạm ứng",
                "Số tiền bằng chữ"
        );
        int i = 0;
        for (String content : contents) {
            DataBody dataBody = new DataBody();
            dataBody.setContent(content);
            dataBody.setTitle(titles.get(i));
            dataBody.setTypeData(types.get(i));
            dataBodyList.add(dataBody);

            i++;
        }
        return dataBodyList;
    }
}