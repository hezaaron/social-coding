/**
 * 
 */
$(document).ready(function() {
	
	var userChoices = {};
	var id = $('#examid').val();
	var q = $('#switchQuestion');
	var qNum;
	
	loadQuestions(id, function(question){
		for(var i in question) {
			qNum = Number(i) + 1;
			q.append($('<option />').val(question[i].id).text(qNum));
		}
		qNum = 1;
		$('#question').text(qNum +'. '+ question[0].problemDescription);
		loadChoices(question[0].id, question[0].multiAnswer);
	});
	
	$('#go').click(handleCombo);

	function handleCombo() {
		var elem = $('#switchQuestion option:selected');
		var index = elem.index();
		qNum = Number(index) + 1;
		loadQuestions(id, function(questions){
			$('#question').text(qNum +'. '+ questions[index].problemDescription);
			loadChoices(questions[index].id, questions[index].multiAnswer);
		})
		
		$('switchQuestion').change();
	}
	
	function loadQuestions(id, callback) {
		var questionData = new Array();
		$.ajax({
			url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/questions/' + id,
			dataType: 'json',
			type: 'GET',
			success: function (data, status){
				$.each(data, function(i, v){
					questionData.push({'id': v.id, 'multiAnswer': v.multiAnswer, 'problemDescription': v.problemDescription});
				});
				if(typeof callback === "function"){
					callback(questionData);
				}
			},
			error: function(){				
			}
		});
	}
	
	function loadChoices(id, isMulti) {
		var choices = $('#choices');
		choices.empty();
		
		$.ajax({
			url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/choices/' + id,
			dataType: 'json',
			type: 'GET',
			success: function (data, status) {
				var elemType = isMulti ? 'checkbox' : 'radio';
				$.each(data, function (i, v) {
					var btn = $('<input />', {type: elemType, name: 'choice', value: v.id, id: v.id});
					if(userChoices[v.id]){
						btn.attr('checked', true);
					}
					var label = $('<label />', {html: btn}).append(' ' + v.choiceText);
					choices.append(btn, [label, $('<br />'), $('<br />')]);
				})
			},
			error: function(){
			}
		});
	}
	
	function handleNext() {
		var choices = $('#choices').children('input');
		$.each(choices, function(i, elem) {
			userChoices[elem.id] = elem.checked;
		})
		
		var elem = $('#switchQuestion option:selected');
		var length = $('#switchQuestion').children('option').length;
		var index = elem.index;
		
		if(index == (length - 1)) {
			$('#switchQuestion option:first-child').attr('selected', 'selected');
		}
		else{
			$('#switchQuestion option:selected').next('option').attr('selected', 'selected');
		}
		
		$('#switchQuestion').change();
	}
});