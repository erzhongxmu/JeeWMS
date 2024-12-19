// 导出页面为PDF格式
import html2Canvas from 'html2canvas'
import JsPDF from 'jspdf'
// require('../../static/font/simhei-normal')export default {
    export default {
	install(Vue, options) {
		Vue.prototype.getPdf = function(id,title) {
			var title = title //
			html2Canvas(document.querySelector(id), {
				allowTaint: true,
				taintTest: false,
				useCORS: true,
				// y: 72, // 对Y轴进行裁切
				// width:1200,
				// height:5000,
				dpi: window.devicePixelRatio * 4, //将分辨率提高到特定的DPI 提高四倍
				scale: 4 //按比例增加分辨率 
			}).then(function(canvas) {
				let contentWidth = canvas.width
				let contentHeight = canvas.height
				let pageHeight = contentWidth / 592.28 * 841.89
				let leftHeight = contentHeight
				let position = 0
				let imgWidth = 595.28
				let imgHeight = 592.28 / contentWidth * contentHeight
				let pageData = canvas.toDataURL('image/jpeg', 1.0)
				let PDF = new JsPDF('', 'pt', 'a4')
				if (leftHeight < pageHeight) {
					PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
				} else {
					while (leftHeight > 0) {
						PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
						leftHeight -= pageHeight
						position -= 841.89
						if (leftHeight > 0) {
							PDF.addPage()
						}
					}
				}
				PDF.save(title + '.pdf')
			})
		}
	}
}
