package com.delaroystudios.quiz;

public class Question {
	private String question;
	private String opta;
	private String optb;
	private String optc;
	private String answer;

	public Question(String question, String optA, String optB, String optC, String answer){
		this.question = question;
		this.opta = optA;
		this.optb = optB;
		this.optc = optC;
		this.answer = answer;
	}

	public Question(){
		question = "fewfwef";
		opta = "fefw";
		optc = "fewf";
		optb = "few";
		answer = "fwfe";

	}



	public String getQuestion() {
		return question;
	}
	public String getOpta() {
		return opta;
	}
	public String getOptb() {
		return optb;
	}
	public String getOptc() {
		return optc;
	}
	public String getAnswer() {
		return answer;
	}

	public void setQuestion(String qUESTION) {
		question = qUESTION;
	}
	public void setOpta(String oPTA) {
		opta = oPTA;
	}
	public void setOptb(String oPTB) {
		optb = oPTB;
	}
	public void setOptc(String oPTC) {
		optc = oPTC;
	}
	public void setAnswer(String aNSWER) {
		answer = aNSWER;
	}

}
