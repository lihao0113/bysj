ajax(path + "/user/pageAll", {pageNumber:1,pageSize:2},pageTest);
function pageTest(res) {
    if (res.code == 1){
        var data = res.data;
        console.info(data);
    }
}
