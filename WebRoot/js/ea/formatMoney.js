jQuery.extend({  
  /** 
    * 金额千位格式化函数 
    */
  fAmount:function(s, n){  
      n = n > 0 && n <= 20 ? n : 2;     
      s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";     
      var l = s.split(".")[0].split("").reverse(),  
      r = s.split(".")[1];     
      var len = (s.indexOf("-")!= -1) ? l.length - 1 : l.length;  
      t = "";     
      for(i = 0; i < len; i++ ){     
         t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != len ? "," : "");     
      }
      return ((s.indexOf("-")!= -1) ? "-" : "") + t.split("").reverse().join("") + "." + r;
  },
  /** 
    * 整数千位格式化函数 
   */
  fInt:function(s){  
      s = parseInt((s + "").replace(/[^\d-]/g, "")) + "";     
      var l = s.split(".")[0].split("").reverse();     
      t = "";     
    var len = (s.indexOf("-")!= -1) ? l.length - 1 : l.length;  
      for(i = 0; i < len; i++ ){     
         t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != len ? "," : "");     
      }     
   return ((s.indexOf("-")!= -1) ? "-" : "") + t.split("").reverse().join("");     
  },
  /** 
    * 整数千位格式化反向函数 
   */  
  fIntRever:function(s){  
      var arr=s.split(",");  
      var result="";  
      for(var i=0;i<arr.length;i++){  
       result+=arr[i];  
      }   
        
      return parseInt(result);  
  },  
  /** 
    * 日期格式化函数 
   */  
  formatDate:function(pattern,date){  
      function formatNumber(data,format){  
          format = format.length;  
          data = data || 0;  
          return format == 1 ? data : (data = String(Math.pow(10,format) + data)).substr(data.length - format);  
      }  
      return pattern.replace(/([yMdhsm])\1*/g,function(format){  
          switch(format.charAt()){  
           case 'y' :  
               return formatNumber(date.getFullYear(),format);  
           case 'M' :  
               return formatNumber(date.getMonth()+1,format);  
           case 'd' :  
               return formatNumber(date.getDate(),format);  
           case 'w' :  
               return date.getDay()+1;  
           case 'h' :  
               return formatNumber(date.getHours(),format);  
           case 'm' :  
               return formatNumber(date.getMinutes(),format);  
           case 's' :  
               return formatNumber(date.getSeconds(),format);  
          }  
      });  
  },  
  /** 
  *精确小数点N位，四舍五入方式 
  */  
  roundDigit:function(num,n){  
   if(typeof num != "number")  
    return "";  
            return Math.round(num * Math.pow(10, n)) / Math.pow(10, n);  
  },  
  /** 
  * 将数字不满位数补零。 
  */  
  fillLen:function(num, count){  
   var len = ("" + num).length;  
   if(len >= count){  
    return num;  
   }  
   num = '0' + num;  
   return jQuery.fillLen(num, count);  
  },  
  subContent:function(s,c){  
   return s.length > c ? s.substring(0,c) + '...' : s;   
  }  
,dataStore:{  
   data:[],  
   get:function(key){  
    var d = this.data;  
    for(var i = 0; i < d.length; i++){  
     if(key === d[i].k){  
      return d[i].v;  
     }  
    }  
    return null;  
   },  
   put:function(key,value){  
    var d = this.data;  
    var flag = false;  
    for(var i = 0; i < d.length; i++){  
     if(key === d[i].k){  
      d[i].v = value;  
      flag = true;  
      break;  
     }  
    }  
    if(!flag)  
     d.push({k:key,v:value});   
   },  
   clear:function(){  
    this.data = new Array();  
   },  
   size:function(){  
    return this.data.length;  
   }  
  } });  
 //多余字符用...代替，是否以title形式显示全部内容  
 jQuery.fn.subContent = function(c,isShowTitle){  
  return this.each(function(){  
   var s = jQuery.trim(jQuery.text(jQuery(this)));  
   jQuery(this).text(jQuery.subContent(s, c));  
   if(isShowTitle){  
    jQuery(this).attr("title",s);  
   }  
  });  
 };  
   
 /**将数值转换为千分位。jQuery对象扩展: 
  //默认整数， 
  //digit：小数位数 
 */  
 jQuery.fn.fNum = function(digit){  
  return this.each(function(){  
   var val = jQuery.trim(jQuery.text(jQuery(this)));  
   jQuery(this).text(function(){  
    if(digit)  
     return val == "" ? val : jQuery.fAmount(parseFloat(val,10),digit);  
    else  
      return val == "" ? val : jQuery.fInt(parseInt(val));  
   });  
  });  
 };  
   
 /** 
  将比例形式的数据转化为百分比表示  
 */  
 jQuery.fn.percentum = function(digit){  
  return this.each(function(){  
   var val = jQuery.trim(jQuery.text(jQuery(this)));  
   jQuery(this).text(function(){  
    return val == "" ? val : (jQuery.roundDigit(parseFloat(val) * 100,digit) + "%") ;  
   });  
  });  
 };  
   
 jQuery.fn.roundDigit = function(digit){  
  return this.each(function(){  
   var val = jQuery.trim(jQuery.text(jQuery(this)));  
   jQuery(this).text(function(){  
    return val == "" ? val : (jQuery.roundDigit(parseFloat(val),digit));  
   });  
  });  
 };  
   
 /** 
  修改元素是否可用 
 */  
 jQuery.fn.disable = function(flag) {  
     return this.each(function() {  
      this.disabled = typeof this.disabled != "undefined" && flag;  
     });  
    };  