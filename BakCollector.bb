AppTitle "Bak Collector","DO NOT DO THIS WILL BREAK APP, DO ANYWAY?"

Rutedir$="J:\Dropbox\Blitz\"

Include "HideShowWindow.bb"

Graphics 800,600,32,2
SetBuffer(BackBuffer())

ggTrayCreate(SystemProperty$("AppHWND"))
ggTraySetIconFromFile(CurrentDir()+"bak.ico")
ggTraySetToolTip("Bak Collector")
ggTrayShowIcon()

Global hidden=False
Global ExitIt=False
Global timer=CreateTimer(30)
Global Y#

	While Not ExitIt
		If ggTrayPeekLeftClick()>0		
			If Not hidden
				HideWindow()
				hidden=True
			Else				
				ShowWindow()
				hidden=False
			EndIf
			ggTrayClearEvents()
		EndIf

		If Not hidden Then
			Cls
			Text 0,0,"Press Enter or Space to clean"
			
			If KeyHit(28) Or KeyHit(57) Then
				SetBuffer(FrontBuffer())
				Cls
				Print "Cleaning..."
				ChangeDir(Rutedir$)
				CleanUpBak()
				SetBuffer(BackBuffer())
			EndIf
			
			If KeyHit(1) ExitIt=True
			
			WaitTimer(timer)
			VWait
			Flip 0
		EndIf
	Wend

ggTrayDestroy()
End

Function CleanUpBak()
	
	UseDir = ReadDir(CurrentDir())
	file$ = NextFile$(UseDir)
	
	Repeat
		
		Y=Y+1
		
		If Y*FontHeight()>GraphicsHeight()-FontHeight() Then
			Cls
			Y=0
		
		EndIf
		
		Locate 0,Y*FontHeight()
		Print "Now cleaning "+CurrentDir()+"\"+File$
		
		If FileType(file$)=1 And Instr(file$,".bb_bak")<>0 Then
			DeleteFile(file$)
		EndIf
		
		If FileType(file$)=2 And file$<>"" And file$<>"." And file$<>".." And file$<>"$RECYCLE.Bin" Then
			oldDir$=CurrentDir()
			ChangeDir CurrentDir()+"\"+file$
			CleanUpBak()
			ChangeDir oldDir$
		EndIf
		
		.nextFl
		file$ = NextFile$(UseDir)
		If file$="." Or file$=".." Or file$="$RECYCLE.Bin" Then Goto nextFl
		
	Until file$=""
	
	SetBuffer(BackBuffer())
	
End Function