package org.vt.smssec;

public class CMDModule extends ModuleImpl<Command> {

	public CMDModule() {
		setComponent("say", TalkCommand.class);
		setComponent("photo", PhotoCommand.class);
		setComponent("taunt", TauntCommand.class);
	}
}
