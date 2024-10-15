

zk.afterLoad('zul.grid', function(){
	
	zul.grid.Renderer.updateColumnMenuButton = function (wgt) {
		var n = wgt.$n(),
			btn;
	 	if (btn = wgt.$n('btn')) btn.style.height = zk.ie6_ || zk.ie7_  ? n.offsetHeight - 1  + 'px': n.offsetHeight + "px";
	};
});