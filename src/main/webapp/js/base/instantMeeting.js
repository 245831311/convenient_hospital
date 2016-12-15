$(function() {
	
});

$('.icon-remove').click(function(){
	
	layer.confirm('确定删除该议程', {
  btn: ['删除','取消'] //按钮
}, function(){
  layer.msg('删除成功', {
    time: 2000,
    skin:'succ',//20s后自动关闭
   offset:'0',
})
}, function(){
  layer.msg('删除失败', {
    time: 2000,
    skin:'fail',//20s后自动关闭
   offset:'0',
});
});	
})




