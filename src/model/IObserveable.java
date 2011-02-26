package model;

import view.IClassTreeObserver;
import view.IMetaInfoObserver;
import view.IObjectTableObserver;
import view.IPopUpWindowObserver;


public interface IObserveable
{

	void addObserver( IClassTreeObserver obs );

	void addObserver( IObjectTableObserver obs );

	void addObserver( IMetaInfoObserver obs );

	void addObserver( IPopUpWindowObserver obs );

	void removeObserver( IClassTreeObserver obs );

	void removeObserver( IObjectTableObserver obs );

	void removeObserver( IMetaInfoObserver obs );

	void removeObserver( IPopUpWindowObserver obs );

	void notifyClassTreeObserver();

	void notifyObjectTableObserver();

	void notifyMetaInfoObserver();

	void notifyPopUpWindowObserver();
}
