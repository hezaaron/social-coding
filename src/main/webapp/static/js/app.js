/**
 * 
 */
$(document).ready(function() {

	const $examId = $('#examid').val(), $switchQuestion = $('#switchQuestion'),
	$examQuestion = $('#exam-question'), $nextButton = $('#next'),
	$finishButton = $('#finish'), $goButton = $('#go'),
	$userChoices = {}, questionsNotAnswered = new Set();
	let currentQuestion = 0;
	console.log(questionsNotAnswered.size());
	loadQuestions($examId, function(question){
		for(let i in question) {
			$switchQuestion.append($(`<option />`).val(question[i].id).text(Number(i)+1));
		}
	});	
	
	displayQuestion(0);
	
	function displayQuestion(questionIndex) {
		const length = $switchQuestion.children('option').length;
		
		loadQuestions($examId, function(question){
			$examQuestion.text(questionIndex+1 +'. '+ question[questionIndex].problemDescription);
			loadChoices(question[questionIndex].id, question[questionIndex].multiAnswer);
		});	
		
		currentQuestion = questionIndex;
		
		if(currentQuestion === (length - 1)) {
			$nextButton.hide();
		}else {
			$nextButton.show();
		}
		
		userChoiceAnswer(currentQuestion);
	}
	
	function userChoiceAnswer(questionIndex) {
		const choices = $('#choices input[name=""]');
		let questionNumber = questionIndex + 1,
		selector; 
		
		$.each(choices, function(i, elem) {
			$userChoices[elem.id] = elem.checked;
		});
		
		if(selector !== undefined && questionsNotAnswered.has(questionNumber)){
			questionsNotAnswered.delete(questionNumber);
		}else {					
			questionsNotAnswered.add(questionNumber);
		}
	}
	
	$nextButton.click(function() {
		displayQuestion(currentQuestion + 1);
	});

	$goButton.click(function(){
		const $elements = $('#switchQuestion option:selected');
		displayQuestion($elements.index());
	});
	
	$finishButton.click(function() {
		displayQuestion(currentQuestion);
		
		if(questionsNotAnswered.size() > 0) {
			let msg = '';
			for(const questionNumber in questionNotAnswered) {
				msg += questionNotAnswered[questionNumber]+', ';
			}
			localStorage.setItem('message','You have not answered question number ' + msg);
		}else {
			localStorage.setItem('message', 'Welldone! You have answered all questions');
		}
	});
	
	$('#message').append(localStorage.getItem('message'));
	localStorage.clear();

	function loadQuestions(id, callback) {
		let questionData = new Array();
		$.ajax({
			url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/questions/' + id,
			dataType: 'json',
			type: 'GET',
			success: function (data, status){
				$.each(data, function(i, v){
					questionData.push({'id': v.id, 'multiAnswer': v.multiAnswer, 'problemDescription': v.problemDescription});
				});
				if(typeof callback === 'function'){
					callback(questionData);
				}
			},
			error: function(){				
			}
		});
	}
	
	
	function loadChoices(id, isMulti) {
		const choices = $('#choices');
		choices.empty();
		$.ajax({
			url: location.protocol + '//' + location.host + '/online-test-exam-maker' +'/choices/' + id,
			dataType: 'json',
			type: 'GET',
			success: function (data, status) {
				let elemType = isMulti ? 'checkbox' : 'radio';
				$.each(data, function (i, v) {
					let input = `<input type='${elemType}' name='${v.questionId}' value='${v.id}' id='${v.id}' />`;
					if($userChoices[v.id]){
						input.attr('checked', true);
					}
					let label = `<label>${input} ${v.choiceText}</label><br /><br />`;
					choices.append(label);
				});
			},
			error: function(){
			}
		});
	}
	
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