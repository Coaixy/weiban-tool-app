XMLHttpRequest.prototype.reallyOpen = XMLHttpRequest.prototype.open;
XMLHttpRequest.prototype.open = function(method, url, async, user, password) {
    this.reallyOpen(method, url , async, user, password);
};
XMLHttpRequest.prototype.reallySetH = XMLHttpRequest.prototype.setRequestHeader;
XMLHttpRequest.prototype.setRequestHeader = function(name,value){
    if(name == "X-Token"){
        console.log(value);
        android.x(value);
    }
    this.reallySetH(name,value);
};
XMLHttpRequest.prototype.reallySend = XMLHttpRequest.prototype.send;
XMLHttpRequest.prototype.send = function(body) {
    android.send(body);
    console.log(body);
    this.reallySend(body);
};