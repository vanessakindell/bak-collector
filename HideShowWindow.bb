; Hide Window / Show Window includes

Function HideWindow()
	hWnd = SystemProperty$("AppHWND")
	ShowWindowA(hWnd,0)
End Function

Function ShowWindow()
	hWnd = SystemProperty$("AppHWND")
	ShowWindowA(hWnd,5)
End Function