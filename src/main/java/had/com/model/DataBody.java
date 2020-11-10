package had.com.model;

import had.com.MyUtil;
import sun.util.resources.LocaleData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataBody {
    private static final String datePattern = "dd/MM/yyyy";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    String content;
    String title;
    String id;
    String valueDefault;
    int typeData = 0;
    boolean isRequire = true;
    // 0: text - 1:date full - 2:date short - 3:text-area 4:text-format


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

    public boolean isRequire() {
        return isRequire;
    }

    public void setRequire(boolean require) {
        isRequire = require;
    }

    public static List<DataBody> createDataHD() {
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
                "<<b_tax>>",
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
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_DATE_SHORT,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD
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
                "Mã số thuế",
                "Tài khoản",
                "Tài sản định giá",
                "Thời điểm định giá",
                "Giá dịch vụ thẩm định",
                "Thuế GTGT",
                "Tổng thanh toán",
                "Tạm ứng",
                "Số tiền tạm ứng bằng chữ"
        );

        LocalDate currentdate = LocalDate.now();
        String dateNow = dateFormatter.format(new Date());
//        String dateNow = "ngày "+String.format("%02d", currentdate.getDayOfMonth())
//        +" tháng "+" năm 2020";
        List<String> valueDefaults = Arrays.asList(
                "",
                dateNow,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "CÔNG TY CỔ PHẦN DỊCH VỤ TÀI CHÍNH VÀ THẨM ĐỊNH GIÁ TÀI SẢN VIỆT NAM",
                "Ông Lê Mạnh Cường",
                "Giám đốc",
                "Số 19 ngõ 402 Bạch Mai, Hai Bà Trưng, Thành Phố Hà Nội.",
                "024.33840666",
                "0108505609",
                "020070442966 – Ngân hàng thương mại cổ phần Sài Gòn Thương Tín – CN Hà Nội ",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        List<Boolean> listRequire = new ArrayList<>();
        contents.stream().forEach(item -> listRequire.add(true));
        int i = 0;
        for (String content : contents) {
            DataBody dataBody = new DataBody();
            dataBody.setContent(content);
            dataBody.setTitle(titles.get(i));
            dataBody.setTypeData(types.get(i));
            dataBody.setValueDefault(valueDefaults.get(i));
            dataBodyList.add(dataBody);
            i++;
        }
        return dataBodyList;
    }


    public static List<DataBody> createDataFormBC() {
        List<DataBody> dataBodyList = new ArrayList<>();

        List<String> contents = Arrays.asList(
                "<<bc_num_contract>>",
                "<<bc_today>>",
                "<<ct_num_contract>>",
                "<<ct_today>>",
                "<<hd_num_contract>>",
                "<<hd_today>>",

                "<<a_side>>",
                "<<a_address>>",
                "<<a_phone>>",
                "<<a_represent>>",
                "<<a_position>>",

                "<<time_pricing>>",
                "<<accets_pricing>>",
                "<<as_unit>>",
                "<<as_count>>",
                "<<as_1>>",
                "<<as_2>>",
                "<<as_3>>",
                "<<as>>",
                "<<as_char>>"
        );

        List<Integer> types = Arrays.asList(
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,

                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,

                MyUtil.TYPE_DATE_SHORT,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,

                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD
        );
        List<String> titles = Arrays.asList(
                "Báo cáo thẩm định số:",
                "Ngày lập báo cáo",
                "Chứng thư thẩm định số",
                "Ngày lập chứng thư",
                "Hợp đồng thẩm định số",
                "Ngày lập hợp đồng",

                "Tên khách hàng",
                "Địa chỉ khách hàng",
                "Số điện thoại khách hàng",
                "Người đại diện",
                "Chức vụ",

                "Thời điểm thẩm định",
                "Tài sản thẩm định giá",
                "Đơn vị tính",
                "Số lượng",
                "TSSS1",
                "TSSS2",
                "TSSS3",
                "Đơn giá thẩm định",
                "Đơn giá thẩm định bằng chữ"
        );

        LocalDate currentdate = LocalDate.now();
        String dateNow = dateFormatter.format(new Date());
//        String dateNow = "ngày "+String.format("%02d", currentdate.getDayOfMonth())
//        +" tháng "+" năm 2020";
        List<String> valueDefaults = Arrays.asList(
                "",
                dateNow,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        List<Boolean> listRequire = new ArrayList<>();
        contents.stream().forEach(item -> listRequire.add(true));
        int i = 0;
        for (String content : contents) {
            DataBody dataBody = new DataBody();
            dataBody.setContent(content);
            dataBody.setTitle(titles.get(i));
            dataBody.setTypeData(types.get(i));
            dataBody.setValueDefault(valueDefaults.get(i));
            dataBodyList.add(dataBody);
            i++;
        }
        return dataBodyList;
    }
    public static List<DataBody> createDataFormCT() {
        List<DataBody> dataBodyList = new ArrayList<>();

        List<String> contents = Arrays.asList(
                "<<bc_num_contract>>",
                "<<bc_today>>",
                "<<ct_num_contract>>",
                "<<ct_today>>",
                "<<hd_num_contract>>",
                "<<hd_today>>",

                "<<a_side>>",
                "<<a_address>>",
                "<<a_phone>>",
                "<<a_represent>>",
                "<<a_position>>",

                "<<time_pricing>>",
                "<<accets_pricing>>",
                "<<as>>",
                "<<as_char>>"
        );

        List<Integer> types = Arrays.asList(
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,

                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,

                MyUtil.TYPE_DATE_SHORT,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD
        );
        List<String> titles = Arrays.asList(
                "Báo cáo thẩm định số:",
                "Ngày lập báo cáo",
                "Chứng thư thẩm định số",
                "Ngày lập chứng thư",
                "Hợp đồng thẩm định số",
                "Ngày lập hợp đồng",

                "Tên khách hàng",
                "Địa chỉ khách hàng",
                "Số điện thoại khách hàng",
                "Người đại diện",
                "Chức vụ",

                "Thời điểm thẩm định",
                "Tài sản thẩm định giá",
                "Đơn giá thẩm định",
                "Đơn giá thẩm định bằng chữ"
        );

        LocalDate currentdate = LocalDate.now();
        String dateNow = dateFormatter.format(new Date());
//        String dateNow = "ngày "+String.format("%02d", currentdate.getDayOfMonth())
//        +" tháng "+" năm 2020";
        List<String> valueDefaults = Arrays.asList(
                "",
                "",
                "",
                dateNow,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        List<Boolean> listRequire = new ArrayList<>();
        contents.stream().forEach(item -> listRequire.add(true));
        int i = 0;
        for (String content : contents) {
            DataBody dataBody = new DataBody();
            dataBody.setContent(content);
            dataBody.setTitle(titles.get(i));
            dataBody.setTypeData(types.get(i));
            dataBody.setValueDefault(valueDefaults.get(i));
            dataBodyList.add(dataBody);
            i++;
        }
        return dataBodyList;
    }

    public static List<DataBody> createDataNghiemThu() {
        List<DataBody> dataBodyList = new ArrayList<>();

        List<String> contents = Arrays.asList(
                "date_today",
                "<<hd_num_contract>>",
                "<<hd_today>>",
                "<<ct_num_contract>>",
                "<<ct_today>>",
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
                "<<b_tax>>",
                "<<b_bank_account>>",
                "<<total_price>>",
                "<<total_price_char>>",
                "<<advance_money>>",
                "<<price_remain>>",
                "<<price_remain_char>>"
        );

        List<Integer> types = Arrays.asList(
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_FORMATTED_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD
        );
        List<String> titles = Arrays.asList(
                "Ngày lập biên bản nghiệm thu ",
                "Hợp đồng thẩm định giá số:",
                "Ngày lập hợp đồng thẩm định:",
                "Chứng thư thẩm định giá số:",
                "Ngày lập chứng thư thẩm định:",
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
                "Giá trị hợp đồng thực tế",
                "Giá trị hợp đồng thực tế bằng chữ",
                "Số tiền đã tạm ứng",
                "Số tiền cần phải thanh toán",
                "Số tiền cần phải thanh toán"
        );

        LocalDate currentdate = LocalDate.now();
        String dateNow = dateFormatter.format(new Date());
//        String dateNow = "ngày "+String.format("%02d", currentdate.getDayOfMonth())
//        +" tháng "+" năm 2020";
        List<String> valueDefaults = Arrays.asList(
                dateNow,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "CÔNG TY CỔ PHẦN DỊCH VỤ TÀI CHÍNH VÀ THẨM ĐỊNH GIÁ TÀI SẢN VIỆT NAM",
                "Ông Lê Mạnh Cường",
                "Giám đốc",
                "Số 19 ngõ 402 Bạch Mai, Hai Bà Trưng, Thành Phố Hà Nội.",
                "024.33840666",
                "0108505609",
                "020070442966 – Ngân hàng thương mại cổ phần Sài Gòn Thương Tín – CN Hà Nội ",
                "",
                "",
                "",
                "",
                ""
        );

        List<Boolean> listRequire = new ArrayList<>();
        contents.stream().forEach(item -> listRequire.add(true));
        int i = 0;
        for (String content : contents) {
            DataBody dataBody = new DataBody();
            dataBody.setContent(content);
            dataBody.setTitle(titles.get(i));
            dataBody.setTypeData(types.get(i));
            dataBody.setValueDefault(valueDefaults.get(i));
            dataBodyList.add(dataBody);
            i++;
        }
        return dataBodyList;
    }

    public static List<DataBody> createDataFormKs() {
        List<DataBody> dataBodyList = new ArrayList<>();

        List<String> contents = Arrays.asList(
                "<<date_today>>",
                "<<hd_num_contract>>",
                "<<hd_today>>",
                "<<a_side>>",
                "<<a_name1>>",
                "<<a_pos1>>",
                "<<a_name2>>",
                "<<a_pos2>>",
                "<<a_name3>>",
                "<<a_pos3>>",
                "<<phap_ly>>"

        );

        List<Integer> types = Arrays.asList(
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_DATE,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_FIELD,
                MyUtil.TYPE_TEXT_AREA
        );
        List<String> titles = Arrays.asList(
                "Ngày lập biên bản khảo sát ",
                "Hợp đồng thẩm định giá số:",
                "Ngày lập hợp đồng thẩm định:",
                "Bên sử dụng tài sản",
                "Ông (Bà) 1",
                "Chức vụ",
                "Ông (Bà) 1",
                "Chức vụ",
                "Ông (Bà) 1",
                "Chức vụ",
                "Pháp lý"
        );

        LocalDate currentdate = LocalDate.now();
        String dateNow = dateFormatter.format(new Date());
//        String dateNow = "ngày "+String.format("%02d", currentdate.getDayOfMonth())
//        +" tháng "+" năm 2020";
        List<String> valueDefaults = Arrays.asList(
                dateNow,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        List<Boolean> listRequire = new ArrayList<>();
        contents.stream().forEach(item -> listRequire.add(true));
        int i = 0;
        for (String content : contents) {
            DataBody dataBody = new DataBody();
            dataBody.setContent(content);
            dataBody.setTitle(titles.get(i));
            dataBody.setTypeData(types.get(i));
            dataBody.setValueDefault(valueDefaults.get(i));
            dataBodyList.add(dataBody);
            i++;
        }
        return dataBodyList;
    }

}