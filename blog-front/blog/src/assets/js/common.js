// 时间格式转化函数
import dayjs from "dayjs"
export function praseDateStr(data,str){
    str = str || "YYYY-MM-DD HH:mm:ss"
    return dayjs(data).format(str)
}

//markdown解析器：markdown-it的配置
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
export function markdownToHtml(content){
    const md = new MarkdownIt({
        html: true,
        linkify: true,
        typographer: true,
        xhtmlOut:true,
        breaks: true, 
        highlight: function (str, lang) {
            if (lang && hljs.getLanguage(lang)) {
              try {
                return '<pre class="hljs"><code>' +
                       hljs.highlight(str,{language:lang,ignoreIllegals:true}).value +
                       '</code></pre>';
              } catch (__) {}
            }
        
            return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>';
          }
    })
    return md.render(content)
}