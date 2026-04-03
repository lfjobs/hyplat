
function LefttoRight(left, right) {
	var s1 = document.getElementById(left);
	var s2 = document.getElementById(right);
	for (var i = 0; i < s1.length; i++) {
		if (s1.options[i].selected) {
			for (var j = 0; j < s2.length; j++) {
				if (s1.options[i].value == s2.options[j].value) {
				alert("this user is selected");
					return;
				}
			}
			s2.add(new Option(s1.options[i].text, s1.options[i].value));
			s1.remove(i--);
		}
	}
}
function allLeftToRight(left, right) {
	var s1 = document.getElementById(left);
	var s2 = document.getElementById(right);
	for (var i = 0; i < s1.length; i++) {
		var count = 0;
		for (var j = 0; j < s2.length; j++) {
			if (s1.options[i].value == s2.options[j].value) {
				count++;
			}
		}
		if (count == 0) {
			s2.add(new Option(s1.options[i].text, s1.options[i].value));
			s1.remove(i--);
		}
	}
	s1.options.length=0;
}
function RighttoLeft(left, right) {
	var s1 = document.getElementById(left);
	var s2 = document.getElementById(right);
	for (var i = 0; i < s2.length; i++) {
		if (s2.options[i].selected) {
			for (var j = 0; j < s1.length; j++) {
				if (s2.options[i].value == s1.options[j].value) {
				    s2.remove(i--);
					return;
				}
			}
			s1.add(new Option(s2.options[i].text, s2.options[i].value));
			s2.remove(i--);
		}
	}
}
function allRightToLeft(left, right) {
	var s1 = document.getElementById(left);
	var s2 = document.getElementById(right);
	for (var i = 0; i < s2.length; i++) {
		var count = 0;
		for (var j = 0; j < s1.length; j++) {
			if (s2.options[i].value == s1.options[j].value) {
				count++;
			}
		}
		if (count == 0) {
			s1.add(new Option(s2.options[i].text, s2.options[i].value));
			s2.remove(i--);
		}
	}
	s2.options.length=0;
}
function fillLeft(left, list) {
	left.length = 0;
	for (var i = 0; i < list.length - 1; i++) {
		var dd = list[i].split(",");
		var option = new Option(dd[1], dd[0]);
		left.add(option);
	}
}

