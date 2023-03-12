import dayjs from "dayjs"
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.css'

// 时间格式转化函数
export function praseDateStr(data,str){
    str = str || "YYYY-MM-DD HH:mm:ss"
    return dayjs(data).format(str)
}

//markdown解析器
export function markdownToHtml(content){
    const md = new MarkdownIt({
        html: true,
        linkify: true,
        typographer: true,
        highlight: function (str, lang) {
          // 当前时间加随机数生成唯一的id标识
          const codeIndex = parseInt(Date.now()) + Math.floor(Math.random() * 10000000)
          //复制按钮
          let btnHtml = `<div class="copy-btn" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}">复制</div>`
          const linesLength = str.split(/\n/).length - 1
          // 生成行号
          let linesNum = '<div aria-hidden="true" class="line-numbers-rows">'
          for (let index = 1; index < linesLength; index++) {
            linesNum = linesNum + '<span>'+ index +'</span>'
          }
          linesNum += '</div>'
          if (lang && hljs.getLanguage(lang)) {
            try {
                // highlight.js 高亮代码
                const html = hljs.highlight(lang, str, true).value
                let langHtml
                if (linesLength) {
                  langHtml = '<b class="language-name">' + lang + '</b>'
                }
                return `<div class="code-block">${btnHtml}<pre class="hljs"><div id="copy${codeIndex}" class="code-content">${html}</div></pre>${linesNum}${langHtml}</div>`
            } catch (error) {
                console.log(error)
            }
          }
          const preCode = md.utils.escapeHtml(str)
          html = html + preCode
          return `<pre class="hljs"><code id="copy${codeIndex}">${html}</code>${linesNum}</pre>`
        }
      })
    return md.render(content)
}

//获取文章目录
export function getCatalog(articleElement){
    let titles = []
    let levels = ["h1","h2"]
    if (!articleElement) {
        return titles
    }
    let elements = Array.from(articleElement.querySelectorAll("*"))

    // 调整标签等级
    let tagNames = new Set(
        elements.map((el) => el.tagName.toLowerCase())
    )
    for (let i = levels.length - 1; i >= 0; i--) {
        if (!tagNames.has(levels[i])) {
            levels.splice(i, 1)
        }
    }

    let serialNumbers = levels.map(() => 0)
    for (let i = 0; i < elements.length; i++) {
        const element = elements[i]
        let tagName = element.tagName.toLowerCase()
        let level = levels.indexOf(tagName)
        if (level == -1) continue

        let id = tagName + "-" + element.innerText + "-" + i
        let node = {
            id,
            level,
            parent: null,
            children: [],
            rawName: element.innerText,
            scrollTop: element.offsetTop,
        }

        if (titles.length > 0) {
            let lastNode = titles.at(-1)

            // 遇到子标题
            if (lastNode.level < node.level) {
                node.parent = lastNode
                lastNode.children.push(node)
            }
            // 遇到上一级标题
            else if (lastNode.level > node.level) {
                serialNumbers.fill(0, level + 1)
                let parent = lastNode.parent
                while (parent) {
                    if (parent.level < node.level) {
                        parent.children.push(node)
                        node.parent = parent
                        break
                    }
                    parent = parent.parent
                }
            }
            // 遇到平级
            else if (lastNode.parent) {
                node.parent = lastNode.parent
                lastNode.parent.children.push(node)
            }
        }

        serialNumbers[level] += 1
        let serialNumber = serialNumbers.slice(0, level + 1).join(".")

        node.isVisible = node.parent == null
        // node.name = serialNumber + ". " + element.innerText
        node.name = element.innerText
        titles.push(node)
    }
    return titles
}

/**
 * 创建代码块
 */
export function buildCodeBlock(element) {
    debugger
    highlightCode(element)
    // buildLineNumber(element)
    // buildCopyButton(element)
}

//高亮代码块
function highlightCode(element) {
    const codeEls = element.querySelectorAll('pre code')
    codeEls.forEach((el) => {
        hljs.highlightBlock(el)
    });
}

