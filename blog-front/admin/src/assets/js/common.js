// 公共js类
import dayjs from "dayjs";




// 时间格式转化函数
export function praseDateStr(data,str){
    str = str || "YYYY-MM-DD HH:mm:ss"
    return dayjs(data).format(str);
}