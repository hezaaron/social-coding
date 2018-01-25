/**
 * 
 */

$(document).ready(function() {
	var userChoices = {}, unAnswered = [], id = $('#examid').val(), qNum;
		
	loadQuestions(id, function(question){
		for(var i in question) {
			qNum = Number(i) + 1;
			$('#switchQuestion').append($('<option />').val(question[i].id).text(qNum));
		}
		qNum = 1;
		$('#question').text(qNum +'. '+ question[0].problemDescription);
		loadChoices(question[0].id, question[0].multiAnswer);
	});
	
	$('#go').click(handleCombo);
	$('#next').click(handleNext);
	$('#finish').click(handleFinish);
	
	var feature = (function() {
		var noSelection = [], qNumber;		
		return {
			questionNumber: function(question) {
				if(question < 10) {
					qNumber = Number(question.charAt(0))
				}else {
					qNumber = Number(question);
				}
				return qNumber; 
			},
		
			getUserChoices: function(choices, question) {
				$.each(choices, function(i,elem){
					userChoices[elem.id] = elem.checked;
					noSelection[elem.id] = elem.checked;
				});

				if((noSelection.filter(i => i === false).length === choices.length) && ($.inArray(this.questionNumber(question), unAnswered) == -1)){
					unAnswered.push(this.questionNumber(question));
				} else if((noSelection.filter(i => i === false).length !== choices.length) && ($.inArray(this.questionNumber(question), unAnswered) != -1)){
					unAnswered = unAnswered.filter(val => val !== this.questionNumber(question));
				}
				noSelection.length = 0;
			}
		};
	})();
		
	function handleCombo() {
		var elem = $('#switchQuestion option:selected');
		var index = elem.index();
		qNum = Number(index) + 1;
		loadQuestions(id, function(question){
			$('#question').text(qNum +'. '+ question[index].problemDescription);
			loadChoices(question[index].id, question[index].multiAnswer);
		});
	}
	
	function handleNext() {
		var choices = $('#choices').children('input'),
		question = $('#question').text().slice(0,2),
		length = $('#switchQuestion').children('option').length;
		
		feature.getUserChoices(choices, question);
		qNum = feature.questionNumber(question);
										
		if(qNum < length) {
			var nextQuestion = qNum + 1;
			loadQuestions(id, function(question){
				$('#question').text(nextQuestion +'. '+ question[qNum].problemDescription);
				loadChoices(question[qNum].id, question[qNum].multiAnswer);
			});
		}
		if(qNum == (length - 1)) $('#next').hide();
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
		
	function handleFinish() {
		var choices = $('#choices').children('input'),
		question = $('#question').text().slice(0,2),
		length = $('#switchQuestion').children('option').length;
		
		feature.getUserChoices(choices, question);
		qNum = feature.questionNumber(question);
		
		if(unAnswered.length > 0) {
			var msg = '';
			for(var i in unAnswered) {
				msg += unAnswered[i]+', ';
			}
			localStorage.setItem('message','You have not answered question(s) ' + msg);
		}else {
			localStorage.setItem('message', 'Welldone! You have answered all questions');
		}
	}
	
	$('#message').append(localStorage.getItem('message'));

	function timerFunction() {
		var countDownTimer = parseInt($('#examtime').val());
		
		setInterval(function() {
			if(--countDownTimer < 0){
				$('#finish').click();
			}
		}, 1000);
		
		setInterval(function(){
			var min = ((countDownTimer / 60) >> 0);
			var sec = (countDownTimer % 60);
			
			$('#time').text(('00' + min).slice(-2) + ':' + ('00' + sec).slice(-2))
		}, 2000);
	}

	timerFunction();
});