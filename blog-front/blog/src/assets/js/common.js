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
    const md = new MarkdownIt()
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
        node.name = serialNumber + ". " + element.innerText
        titles.push(node)
    }
    return titles
}

