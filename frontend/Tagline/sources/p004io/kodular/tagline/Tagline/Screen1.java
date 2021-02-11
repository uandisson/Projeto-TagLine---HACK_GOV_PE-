package p004io.kodular.tagline.Tagline;

import android.support.p000v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.MakeroidListViewImageText;
import com.google.appinventor.components.runtime.MakeroidViewPager;
import com.google.appinventor.components.runtime.Map;
import com.google.appinventor.components.runtime.Marker;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.PushNotifications;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.C1234runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.C1259lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;

/* renamed from: io.kodular.tagline.Tagline.Screen1 */
/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33265), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33261), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33256);
    static final FString Lit100;
    static final FString Lit101;
    static final SimpleSymbol Lit102;
    static final DFloNum Lit103 = DFloNum.make(-8.057020063266835d);
    static final DFloNum Lit104 = DFloNum.make(-34.89618301391602d);
    static final FString Lit105;
    static final FString Lit106;
    static final SimpleSymbol Lit107;
    static final DFloNum Lit108 = DFloNum.make(-8.071127071674166d);
    static final DFloNum Lit109 = DFloNum.make(-34.89601135253907d);
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33492), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33488), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33483);
    static final FString Lit110;
    static final SimpleSymbol Lit111;
    static final SimpleSymbol Lit112;
    static final PairWithPosition Lit113;
    static final SimpleSymbol Lit114;
    static final SimpleSymbol Lit115;
    static final FString Lit116;
    static final SimpleSymbol Lit117;
    static final IntNum Lit118;
    static final FString Lit119;
    static final PairWithPosition Lit12 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33719), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33715), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33710);
    static final FString Lit120;
    static final FString Lit121;
    static final FString Lit122;
    static final IntNum Lit123;
    static final FString Lit124;
    static final FString Lit125;
    static final SimpleSymbol Lit126;
    static final FString Lit127;
    static final FString Lit128;
    static final SimpleSymbol Lit129;
    static final PairWithPosition Lit13 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33756), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33752), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33748), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33743);
    static final SimpleSymbol Lit130;
    static final SimpleSymbol Lit131;
    static final IntNum Lit132 = IntNum.make(20);
    static final SimpleSymbol Lit133;
    static final SimpleSymbol Lit134;
    static final IntNum Lit135 = IntNum.make(1);
    static final FString Lit136;
    static final FString Lit137;
    static final SimpleSymbol Lit138;
    static final IntNum Lit139;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit140;
    static final IntNum Lit141;
    static final SimpleSymbol Lit142;
    static final IntNum Lit143 = IntNum.make(18);
    static final FString Lit144;
    static final FString Lit145;
    static final IntNum Lit146;
    static final SimpleSymbol Lit147;
    static final SimpleSymbol Lit148;
    static final SimpleSymbol Lit149;
    static final PairWithPosition Lit15 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37141), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37137), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37132);
    static final IntNum Lit150;
    static final SimpleSymbol Lit151;
    static final FString Lit152;
    static final FString Lit153;
    static final SimpleSymbol Lit154;
    static final FString Lit155;
    static final SimpleSymbol Lit156;
    static final SimpleSymbol Lit157;
    static final SimpleSymbol Lit158;
    static final SimpleSymbol Lit159;
    static final PairWithPosition Lit16 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37386), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37382), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37377);
    static final SimpleSymbol Lit160;
    static final SimpleSymbol Lit161;
    static final SimpleSymbol Lit162;
    static final SimpleSymbol Lit163;
    static final SimpleSymbol Lit164;
    static final SimpleSymbol Lit165;
    static final SimpleSymbol Lit166;
    static final SimpleSymbol Lit167;
    static final SimpleSymbol Lit168;
    static final SimpleSymbol Lit169;
    static final PairWithPosition Lit17 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37581), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37577), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37572);
    static final SimpleSymbol Lit170;
    static final SimpleSymbol Lit171;
    static final SimpleSymbol Lit172;
    static final SimpleSymbol Lit173;
    static final PairWithPosition Lit18 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37790), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37786), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37781);
    static final PairWithPosition Lit19 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37827), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37823), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37819), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37814);
    static final SimpleSymbol Lit2;
    static final PairWithPosition Lit20 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37141), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37137), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37132);
    static final PairWithPosition Lit21 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37386), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37382), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37377);
    static final PairWithPosition Lit22 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37581), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37577), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37572);
    static final PairWithPosition Lit23 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37790), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37786), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37781);
    static final PairWithPosition Lit24 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37827), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37823), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37819), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 37814);
    static final SimpleSymbol Lit25;
    static final PairWithPosition Lit26 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41126), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41121);
    static final PairWithPosition Lit27 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41245), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41240);
    static final PairWithPosition Lit28 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41366), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41361);
    static final PairWithPosition Lit29 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41487), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41482);
    static final SimpleSymbol Lit3;
    static final PairWithPosition Lit30 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41524), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41520), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41516), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41511);
    static final PairWithPosition Lit31 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41126), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41121);
    static final PairWithPosition Lit32 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41245), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41240);
    static final PairWithPosition Lit33 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41366), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41361);
    static final PairWithPosition Lit34 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41487), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41482);
    static final PairWithPosition Lit35 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41524), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41520), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41516), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 41511);
    static final SimpleSymbol Lit36;
    static final IntNum Lit37;
    static final SimpleSymbol Lit38;
    static final SimpleSymbol Lit39;
    static final PairWithPosition Lit4 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33040), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33036), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33031);
    static final SimpleSymbol Lit40;
    static final SimpleSymbol Lit41;
    static final SimpleSymbol Lit42;
    static final SimpleSymbol Lit43;
    static final SimpleSymbol Lit44;
    static final SimpleSymbol Lit45;
    static final SimpleSymbol Lit46;
    static final SimpleSymbol Lit47;
    static final PairWithPosition Lit48 = PairWithPosition.make(Lit172, PairWithPosition.make(Lit40, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90235), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90224);
    static final SimpleSymbol Lit49;
    static final PairWithPosition Lit5 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33265), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33261), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33256);
    static final PairWithPosition Lit50 = PairWithPosition.make(Lit172, PairWithPosition.make(Lit40, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90383), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90372);
    static final SimpleSymbol Lit51;
    static final PairWithPosition Lit52 = PairWithPosition.make(Lit172, PairWithPosition.make(Lit40, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90527), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90516);
    static final SimpleSymbol Lit53;
    static final SimpleSymbol Lit54;
    static final SimpleSymbol Lit55;
    static final PairWithPosition Lit56 = PairWithPosition.make(Lit171, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90668);
    static final SimpleSymbol Lit57;
    static final PairWithPosition Lit58 = PairWithPosition.make(Lit171, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 90829);
    static final SimpleSymbol Lit59;
    static final PairWithPosition Lit6 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33492), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33488), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33483);
    static final SimpleSymbol Lit60;
    static final FString Lit61;
    static final FString Lit62;
    static final FString Lit63;
    static final SimpleSymbol Lit64;
    static final SimpleSymbol Lit65;
    static final IntNum Lit66 = IntNum.make(-2);
    static final SimpleSymbol Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69;
    static final PairWithPosition Lit7 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33719), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33715), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33710);
    static final SimpleSymbol Lit70;
    static final SimpleSymbol Lit71;
    static final FString Lit72;
    static final FString Lit73;
    static final SimpleSymbol Lit74;
    static final SimpleSymbol Lit75;
    static final SimpleSymbol Lit76;
    static final DFloNum Lit77 = DFloNum.make(-8.064328574839289d);
    static final SimpleSymbol Lit78;
    static final DFloNum Lit79 = DFloNum.make(-34.90493774414063d);
    static final PairWithPosition Lit8 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33756), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33752), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33748), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33743);
    static final FString Lit80;
    static final FString Lit81;
    static final SimpleSymbol Lit82;
    static final DFloNum Lit83 = DFloNum.make(-8.045972062733982d);
    static final DFloNum Lit84 = DFloNum.make(-34.88948822021485d);
    static final FString Lit85;
    static final FString Lit86;
    static final SimpleSymbol Lit87;
    static final DFloNum Lit88 = DFloNum.make(-8.080304860930505d);
    static final DFloNum Lit89 = DFloNum.make(-34.90699768066407d);
    static final PairWithPosition Lit9 = PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, PairWithPosition.make(Lit173, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33040), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33036), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 33031);
    static final FString Lit90;
    static final FString Lit91;
    static final SimpleSymbol Lit92;
    static final DFloNum Lit93 = DFloNum.make(-8.064052383488752d);
    static final DFloNum Lit94 = DFloNum.make(-34.889123439788825d);
    static final FString Lit95;
    static final FString Lit96;
    static final SimpleSymbol Lit97;
    static final DFloNum Lit98 = DFloNum.make(-8.0651359023208d);
    static final DFloNum Lit99 = DFloNum.make(-34.885132312774665d);
    public static Screen1 Screen1;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    static final ModuleMethod proc$Fn6 = null;
    static final ModuleMethod proc$Fn7 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public HorizontalArrangement Horizontal_Arrangement1;
    public HorizontalArrangement Horizontal_Arrangement2;
    public Label Label1;
    public MakeroidListViewImageText List_View_Image_and_Text1;
    public MakeroidListViewImageText List_View_Image_and_Text4;
    public Map Map1;
    public Marker Marker1;
    public Marker Marker2;
    public Marker Marker3;
    public Marker Marker4;
    public Marker Marker5;
    public Marker Marker6;
    public Marker Marker7;
    public final ModuleMethod Marker7$Click;
    public Notifier Notifier1;
    public PushNotifications Push_Notifications1;
    public final ModuleMethod Screen1$Initialize;
    public VerticalArrangement Vertical_Arrangement1;
    public MakeroidViewPager View_Pager1;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public final ModuleMethod dispatchGenericEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public final ModuleMethod get$Mnsimple$Mnname;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    static {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        SimpleSymbol simpleSymbol3;
        SimpleSymbol simpleSymbol4;
        SimpleSymbol simpleSymbol5;
        SimpleSymbol simpleSymbol6;
        SimpleSymbol simpleSymbol7;
        SimpleSymbol simpleSymbol8;
        SimpleSymbol simpleSymbol9;
        SimpleSymbol simpleSymbol10;
        SimpleSymbol simpleSymbol11;
        SimpleSymbol simpleSymbol12;
        SimpleSymbol simpleSymbol13;
        SimpleSymbol simpleSymbol14;
        SimpleSymbol simpleSymbol15;
        SimpleSymbol simpleSymbol16;
        SimpleSymbol simpleSymbol17;
        SimpleSymbol simpleSymbol18;
        FString fString;
        SimpleSymbol simpleSymbol19;
        FString fString2;
        FString fString3;
        SimpleSymbol simpleSymbol20;
        SimpleSymbol simpleSymbol21;
        SimpleSymbol simpleSymbol22;
        SimpleSymbol simpleSymbol23;
        FString fString4;
        FString fString5;
        SimpleSymbol simpleSymbol24;
        SimpleSymbol simpleSymbol25;
        SimpleSymbol simpleSymbol26;
        FString fString6;
        FString fString7;
        SimpleSymbol simpleSymbol27;
        SimpleSymbol simpleSymbol28;
        SimpleSymbol simpleSymbol29;
        SimpleSymbol simpleSymbol30;
        SimpleSymbol simpleSymbol31;
        FString fString8;
        FString fString9;
        SimpleSymbol simpleSymbol32;
        FString fString10;
        FString fString11;
        FString fString12;
        FString fString13;
        FString fString14;
        FString fString15;
        SimpleSymbol simpleSymbol33;
        FString fString16;
        SimpleSymbol simpleSymbol34;
        SimpleSymbol simpleSymbol35;
        SimpleSymbol simpleSymbol36;
        SimpleSymbol simpleSymbol37;
        SimpleSymbol simpleSymbol38;
        FString fString17;
        SimpleSymbol simpleSymbol39;
        FString fString18;
        FString fString19;
        SimpleSymbol simpleSymbol40;
        FString fString20;
        FString fString21;
        SimpleSymbol simpleSymbol41;
        FString fString22;
        FString fString23;
        SimpleSymbol simpleSymbol42;
        FString fString24;
        FString fString25;
        SimpleSymbol simpleSymbol43;
        FString fString26;
        FString fString27;
        SimpleSymbol simpleSymbol44;
        FString fString28;
        FString fString29;
        SimpleSymbol simpleSymbol45;
        SimpleSymbol simpleSymbol46;
        SimpleSymbol simpleSymbol47;
        SimpleSymbol simpleSymbol48;
        FString fString30;
        FString fString31;
        SimpleSymbol simpleSymbol49;
        SimpleSymbol simpleSymbol50;
        SimpleSymbol simpleSymbol51;
        SimpleSymbol simpleSymbol52;
        SimpleSymbol simpleSymbol53;
        SimpleSymbol simpleSymbol54;
        SimpleSymbol simpleSymbol55;
        FString fString32;
        FString fString33;
        FString fString34;
        SimpleSymbol simpleSymbol56;
        SimpleSymbol simpleSymbol57;
        SimpleSymbol simpleSymbol58;
        SimpleSymbol simpleSymbol59;
        SimpleSymbol simpleSymbol60;
        SimpleSymbol simpleSymbol61;
        SimpleSymbol simpleSymbol62;
        SimpleSymbol simpleSymbol63;
        SimpleSymbol simpleSymbol64;
        SimpleSymbol simpleSymbol65;
        SimpleSymbol simpleSymbol66;
        SimpleSymbol simpleSymbol67;
        SimpleSymbol simpleSymbol68;
        SimpleSymbol simpleSymbol69;
        SimpleSymbol simpleSymbol70;
        SimpleSymbol simpleSymbol71;
        SimpleSymbol simpleSymbol72;
        SimpleSymbol simpleSymbol73;
        SimpleSymbol simpleSymbol74;
        SimpleSymbol simpleSymbol75;
        SimpleSymbol simpleSymbol76;
        SimpleSymbol simpleSymbol77;
        SimpleSymbol simpleSymbol78;
        SimpleSymbol simpleSymbol79;
        new SimpleSymbol("any");
        Lit173 = (SimpleSymbol) simpleSymbol.readResolve();
        new SimpleSymbol("component");
        Lit172 = (SimpleSymbol) simpleSymbol2.readResolve();
        new SimpleSymbol("list");
        Lit171 = (SimpleSymbol) simpleSymbol3.readResolve();
        new SimpleSymbol("proc");
        Lit170 = (SimpleSymbol) simpleSymbol4.readResolve();
        new SimpleSymbol("lookup-handler");
        Lit169 = (SimpleSymbol) simpleSymbol5.readResolve();
        new SimpleSymbol("dispatchGenericEvent");
        Lit168 = (SimpleSymbol) simpleSymbol6.readResolve();
        new SimpleSymbol("dispatchEvent");
        Lit167 = (SimpleSymbol) simpleSymbol7.readResolve();
        new SimpleSymbol("send-error");
        Lit166 = (SimpleSymbol) simpleSymbol8.readResolve();
        new SimpleSymbol("add-to-form-do-after-creation");
        Lit165 = (SimpleSymbol) simpleSymbol9.readResolve();
        new SimpleSymbol("add-to-global-vars");
        Lit164 = (SimpleSymbol) simpleSymbol10.readResolve();
        new SimpleSymbol("add-to-components");
        Lit163 = (SimpleSymbol) simpleSymbol11.readResolve();
        new SimpleSymbol("add-to-events");
        Lit162 = (SimpleSymbol) simpleSymbol12.readResolve();
        new SimpleSymbol("add-to-global-var-environment");
        Lit161 = (SimpleSymbol) simpleSymbol13.readResolve();
        new SimpleSymbol("is-bound-in-form-environment");
        Lit160 = (SimpleSymbol) simpleSymbol14.readResolve();
        new SimpleSymbol("lookup-in-form-environment");
        Lit159 = (SimpleSymbol) simpleSymbol15.readResolve();
        new SimpleSymbol("add-to-form-environment");
        Lit158 = (SimpleSymbol) simpleSymbol16.readResolve();
        new SimpleSymbol("android-log-form");
        Lit157 = (SimpleSymbol) simpleSymbol17.readResolve();
        new SimpleSymbol("get-simple-name");
        Lit156 = (SimpleSymbol) simpleSymbol18.readResolve();
        new FString("com.google.appinventor.components.runtime.PushNotifications");
        Lit155 = fString;
        new SimpleSymbol("Push_Notifications1");
        Lit154 = (SimpleSymbol) simpleSymbol19.readResolve();
        new FString("com.google.appinventor.components.runtime.PushNotifications");
        Lit153 = fString2;
        new FString("com.google.appinventor.components.runtime.Notifier");
        Lit152 = fString3;
        new SimpleSymbol("UseBackgroundColor");
        Lit151 = (SimpleSymbol) simpleSymbol20.readResolve();
        int[] iArr = new int[2];
        iArr[0] = -18737649;
        Lit150 = IntNum.make(iArr);
        new SimpleSymbol("TextColor");
        Lit149 = (SimpleSymbol) simpleSymbol21.readResolve();
        new SimpleSymbol("Linkify");
        Lit148 = (SimpleSymbol) simpleSymbol22.readResolve();
        new SimpleSymbol("LightTheme");
        Lit147 = (SimpleSymbol) simpleSymbol23.readResolve();
        int[] iArr2 = new int[2];
        iArr2[0] = -769226;
        Lit146 = IntNum.make(iArr2);
        new FString("com.google.appinventor.components.runtime.Notifier");
        Lit145 = fString4;
        new FString("com.google.appinventor.components.runtime.MakeroidListViewImageText");
        Lit144 = fString5;
        new SimpleSymbol("TitleTextSize");
        Lit142 = (SimpleSymbol) simpleSymbol24.readResolve();
        int[] iArr3 = new int[2];
        iArr3[0] = -12627531;
        Lit141 = IntNum.make(iArr3);
        new SimpleSymbol("TitleColor");
        Lit140 = (SimpleSymbol) simpleSymbol25.readResolve();
        int[] iArr4 = new int[2];
        iArr4[0] = -81913;
        Lit139 = IntNum.make(iArr4);
        new SimpleSymbol("SubtitleColor");
        Lit138 = (SimpleSymbol) simpleSymbol26.readResolve();
        new FString("com.google.appinventor.components.runtime.MakeroidListViewImageText");
        Lit137 = fString6;
        new FString("com.google.appinventor.components.runtime.Label");
        Lit136 = fString7;
        new SimpleSymbol("TextAlignment");
        Lit134 = (SimpleSymbol) simpleSymbol27.readResolve();
        new SimpleSymbol("Text");
        Lit133 = (SimpleSymbol) simpleSymbol28.readResolve();
        new SimpleSymbol("FontSize");
        Lit131 = (SimpleSymbol) simpleSymbol29.readResolve();
        new SimpleSymbol("FontBold");
        Lit130 = (SimpleSymbol) simpleSymbol30.readResolve();
        new SimpleSymbol("Label1");
        Lit129 = (SimpleSymbol) simpleSymbol31.readResolve();
        new FString("com.google.appinventor.components.runtime.Label");
        Lit128 = fString8;
        new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit127 = fString9;
        new SimpleSymbol("Vertical_Arrangement1");
        Lit126 = (SimpleSymbol) simpleSymbol32.readResolve();
        new FString("com.google.appinventor.components.runtime.VerticalArrangement");
        Lit125 = fString10;
        new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit124 = fString11;
        int[] iArr5 = new int[2];
        iArr5[0] = -1;
        Lit123 = IntNum.make(iArr5);
        new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit122 = fString12;
        new FString("com.google.appinventor.components.runtime.MakeroidListViewImageText");
        Lit121 = fString13;
        new FString("com.google.appinventor.components.runtime.MakeroidListViewImageText");
        Lit120 = fString14;
        new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit119 = fString15;
        int[] iArr6 = new int[2];
        iArr6[0] = -1;
        Lit118 = IntNum.make(iArr6);
        new SimpleSymbol("BackgroundColor");
        Lit117 = (SimpleSymbol) simpleSymbol33.readResolve();
        new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit116 = fString16;
        new SimpleSymbol("Click");
        Lit115 = (SimpleSymbol) simpleSymbol34.readResolve();
        new SimpleSymbol("Marker7$Click");
        Lit114 = (SimpleSymbol) simpleSymbol35.readResolve();
        new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT);
        SimpleSymbol simpleSymbol80 = (SimpleSymbol) simpleSymbol36.readResolve();
        Lit40 = simpleSymbol80;
        Lit113 = PairWithPosition.make(simpleSymbol80, PairWithPosition.make(Lit40, PairWithPosition.make(Lit40, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 503947), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 503942), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen1.yail", 503936);
        new SimpleSymbol("ShowMessageDialog");
        Lit112 = (SimpleSymbol) simpleSymbol37.readResolve();
        new SimpleSymbol("Notifier1");
        Lit111 = (SimpleSymbol) simpleSymbol38.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit110 = fString17;
        new SimpleSymbol("Marker7");
        Lit107 = (SimpleSymbol) simpleSymbol39.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit106 = fString18;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit105 = fString19;
        new SimpleSymbol("Marker6");
        Lit102 = (SimpleSymbol) simpleSymbol40.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit101 = fString20;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit100 = fString21;
        new SimpleSymbol("Marker5");
        Lit97 = (SimpleSymbol) simpleSymbol41.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit96 = fString22;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit95 = fString23;
        new SimpleSymbol("Marker4");
        Lit92 = (SimpleSymbol) simpleSymbol42.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit91 = fString24;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit90 = fString25;
        new SimpleSymbol("Marker3");
        Lit87 = (SimpleSymbol) simpleSymbol43.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit86 = fString26;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit85 = fString27;
        new SimpleSymbol("Marker2");
        Lit82 = (SimpleSymbol) simpleSymbol44.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit81 = fString28;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit80 = fString29;
        new SimpleSymbol("Longitude");
        Lit78 = (SimpleSymbol) simpleSymbol45.readResolve();
        new SimpleSymbol("Latitude");
        Lit76 = (SimpleSymbol) simpleSymbol46.readResolve();
        new SimpleSymbol("ImageAsset");
        Lit75 = (SimpleSymbol) simpleSymbol47.readResolve();
        new SimpleSymbol("Marker1");
        Lit74 = (SimpleSymbol) simpleSymbol48.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit73 = fString30;
        new FString("com.google.appinventor.components.runtime.Map");
        Lit72 = fString31;
        new SimpleSymbol("ShowZoom");
        Lit71 = (SimpleSymbol) simpleSymbol49.readResolve();
        new SimpleSymbol("ShowUser");
        Lit70 = (SimpleSymbol) simpleSymbol50.readResolve();
        new SimpleSymbol("boolean");
        Lit69 = (SimpleSymbol) simpleSymbol51.readResolve();
        new SimpleSymbol("ShowScale");
        Lit68 = (SimpleSymbol) simpleSymbol52.readResolve();
        new SimpleSymbol("Width");
        Lit67 = (SimpleSymbol) simpleSymbol53.readResolve();
        new SimpleSymbol("Height");
        Lit65 = (SimpleSymbol) simpleSymbol54.readResolve();
        new SimpleSymbol("CenterFromString");
        Lit64 = (SimpleSymbol) simpleSymbol55.readResolve();
        new FString("com.google.appinventor.components.runtime.Map");
        Lit63 = fString32;
        new FString("com.google.appinventor.components.runtime.MakeroidViewPager");
        Lit62 = fString33;
        new FString("com.google.appinventor.components.runtime.MakeroidViewPager");
        Lit61 = fString34;
        new SimpleSymbol("Initialize");
        Lit60 = (SimpleSymbol) simpleSymbol56.readResolve();
        new SimpleSymbol("Screen1$Initialize");
        Lit59 = (SimpleSymbol) simpleSymbol57.readResolve();
        new SimpleSymbol("List_View_Image_and_Text1");
        Lit57 = (SimpleSymbol) simpleSymbol58.readResolve();
        new SimpleSymbol("$item");
        Lit55 = (SimpleSymbol) simpleSymbol59.readResolve();
        new SimpleSymbol("AddItemFromList");
        Lit54 = (SimpleSymbol) simpleSymbol60.readResolve();
        new SimpleSymbol("List_View_Image_and_Text4");
        Lit53 = (SimpleSymbol) simpleSymbol61.readResolve();
        new SimpleSymbol("Horizontal_Arrangement2");
        Lit51 = (SimpleSymbol) simpleSymbol62.readResolve();
        new SimpleSymbol("Horizontal_Arrangement1");
        Lit49 = (SimpleSymbol) simpleSymbol63.readResolve();
        new SimpleSymbol("Map1");
        Lit47 = (SimpleSymbol) simpleSymbol64.readResolve();
        new SimpleSymbol("AddComponentToView");
        Lit46 = (SimpleSymbol) simpleSymbol65.readResolve();
        new SimpleSymbol("View_Pager1");
        Lit45 = (SimpleSymbol) simpleSymbol66.readResolve();
        new SimpleSymbol("Title");
        Lit44 = (SimpleSymbol) simpleSymbol67.readResolve();
        new SimpleSymbol("Theme");
        Lit43 = (SimpleSymbol) simpleSymbol68.readResolve();
        new SimpleSymbol("PackageName");
        Lit42 = (SimpleSymbol) simpleSymbol69.readResolve();
        new SimpleSymbol("AppName");
        Lit41 = (SimpleSymbol) simpleSymbol70.readResolve();
        new SimpleSymbol("AppId");
        Lit39 = (SimpleSymbol) simpleSymbol71.readResolve();
        new SimpleSymbol("number");
        Lit38 = (SimpleSymbol) simpleSymbol72.readResolve();
        int[] iArr7 = new int[2];
        iArr7[0] = -1;
        Lit37 = IntNum.make(iArr7);
        new SimpleSymbol("AccentColor");
        Lit36 = (SimpleSymbol) simpleSymbol73.readResolve();
        new SimpleSymbol("g$name2");
        Lit25 = (SimpleSymbol) simpleSymbol74.readResolve();
        new SimpleSymbol("g$name3");
        Lit14 = (SimpleSymbol) simpleSymbol75.readResolve();
        new SimpleSymbol("g$name");
        Lit3 = (SimpleSymbol) simpleSymbol76.readResolve();
        new SimpleSymbol("*the-null-value*");
        Lit2 = (SimpleSymbol) simpleSymbol77.readResolve();
        new SimpleSymbol("getMessage");
        Lit1 = (SimpleSymbol) simpleSymbol78.readResolve();
        new SimpleSymbol("Screen1");
        Lit0 = (SimpleSymbol) simpleSymbol79.readResolve();
    }

    public Screen1() {
        ModuleMethod moduleMethod;
        frame frame2;
        ModuleMethod moduleMethod2;
        ModuleMethod moduleMethod3;
        ModuleMethod moduleMethod4;
        ModuleMethod moduleMethod5;
        ModuleMethod moduleMethod6;
        ModuleMethod moduleMethod7;
        ModuleMethod moduleMethod8;
        ModuleMethod moduleMethod9;
        ModuleMethod moduleMethod10;
        ModuleMethod moduleMethod11;
        ModuleMethod moduleMethod12;
        ModuleMethod moduleMethod13;
        ModuleMethod moduleMethod14;
        ModuleMethod moduleMethod15;
        ModuleMethod moduleMethod16;
        ModuleMethod moduleMethod17;
        ModuleMethod moduleMethod18;
        ModuleMethod moduleMethod19;
        ModuleMethod moduleMethod20;
        ModuleMethod moduleMethod21;
        ModuleMethod moduleMethod22;
        ModuleMethod moduleMethod23;
        ModuleMethod moduleMethod24;
        ModuleMethod moduleMethod25;
        ModuleMethod moduleMethod26;
        ModuleMethod moduleMethod27;
        ModuleMethod moduleMethod28;
        ModuleMethod moduleMethod29;
        ModuleMethod moduleMethod30;
        ModuleMethod moduleMethod31;
        ModuleMethod moduleMethod32;
        ModuleMethod moduleMethod33;
        ModuleMethod moduleMethod34;
        ModuleMethod moduleMethod35;
        ModuleMethod moduleMethod36;
        ModuleMethod moduleMethod37;
        ModuleMethod moduleMethod38;
        ModuleMethod moduleMethod39;
        ModuleMethod moduleMethod40;
        ModuleMethod moduleMethod41;
        ModuleMethod moduleMethod42;
        ModuleMethod moduleMethod43;
        ModuleMethod moduleMethod44;
        ModuleMethod moduleMethod45;
        ModuleMethod moduleMethod46;
        ModuleMethod moduleMethod47;
        ModuleMethod moduleMethod48;
        ModuleMethod moduleMethod49;
        ModuleMethod moduleMethod50;
        ModuleMethod moduleMethod51;
        ModuleMethod moduleMethod52;
        ModuleMethod moduleMethod53;
        ModuleInfo.register(this);
        ModuleMethod moduleMethod54 = moduleMethod;
        new frame();
        frame frame3 = frame2;
        frame3.$main = this;
        frame frame4 = frame3;
        new ModuleMethod(frame4, 1, Lit156, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.get$Mnsimple$Mnname = moduleMethod54;
        new ModuleMethod(frame4, 2, Lit157, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = moduleMethod2;
        new ModuleMethod(frame4, 3, Lit158, 8194);
        this.add$Mnto$Mnform$Mnenvironment = moduleMethod3;
        new ModuleMethod(frame4, 4, Lit159, 8193);
        this.lookup$Mnin$Mnform$Mnenvironment = moduleMethod4;
        new ModuleMethod(frame4, 6, Lit160, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = moduleMethod5;
        new ModuleMethod(frame4, 7, Lit161, 8194);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = moduleMethod6;
        new ModuleMethod(frame4, 8, Lit162, 8194);
        this.add$Mnto$Mnevents = moduleMethod7;
        new ModuleMethod(frame4, 9, Lit163, 16388);
        this.add$Mnto$Mncomponents = moduleMethod8;
        new ModuleMethod(frame4, 10, Lit164, 8194);
        this.add$Mnto$Mnglobal$Mnvars = moduleMethod9;
        new ModuleMethod(frame4, 11, Lit165, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = moduleMethod10;
        new ModuleMethod(frame4, 12, Lit166, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = moduleMethod11;
        new ModuleMethod(frame4, 13, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = moduleMethod12;
        new ModuleMethod(frame4, 14, Lit167, 16388);
        this.dispatchEvent = moduleMethod13;
        new ModuleMethod(frame4, 15, Lit168, 16388);
        this.dispatchGenericEvent = moduleMethod14;
        new ModuleMethod(frame4, 16, Lit169, 8194);
        this.lookup$Mnhandler = moduleMethod15;
        new ModuleMethod(frame4, 17, (Object) null, 0);
        ModuleMethod moduleMethod55 = moduleMethod16;
        moduleMethod55.setProperty("source-location", "/tmp/runtime8397583071109955809.scm:615");
        lambda$Fn1 = moduleMethod55;
        new ModuleMethod(frame4, 18, "$define", 0);
        this.$define = moduleMethod17;
        new ModuleMethod(frame4, 19, (Object) null, 0);
        lambda$Fn2 = moduleMethod18;
        new ModuleMethod(frame4, 20, (Object) null, 0);
        lambda$Fn3 = moduleMethod19;
        new ModuleMethod(frame4, 21, (Object) null, 0);
        lambda$Fn4 = moduleMethod20;
        new ModuleMethod(frame4, 22, (Object) null, 0);
        lambda$Fn5 = moduleMethod21;
        new ModuleMethod(frame4, 23, Lit170, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        proc$Fn6 = moduleMethod22;
        new ModuleMethod(frame4, 24, Lit170, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        proc$Fn7 = moduleMethod23;
        new ModuleMethod(frame4, 25, Lit59, 0);
        this.Screen1$Initialize = moduleMethod24;
        new ModuleMethod(frame4, 26, (Object) null, 0);
        lambda$Fn8 = moduleMethod25;
        new ModuleMethod(frame4, 27, (Object) null, 0);
        lambda$Fn9 = moduleMethod26;
        new ModuleMethod(frame4, 28, (Object) null, 0);
        lambda$Fn10 = moduleMethod27;
        new ModuleMethod(frame4, 29, (Object) null, 0);
        lambda$Fn11 = moduleMethod28;
        new ModuleMethod(frame4, 30, (Object) null, 0);
        lambda$Fn12 = moduleMethod29;
        new ModuleMethod(frame4, 31, (Object) null, 0);
        lambda$Fn13 = moduleMethod30;
        new ModuleMethod(frame4, 32, (Object) null, 0);
        lambda$Fn14 = moduleMethod31;
        new ModuleMethod(frame4, 33, (Object) null, 0);
        lambda$Fn15 = moduleMethod32;
        new ModuleMethod(frame4, 34, (Object) null, 0);
        lambda$Fn16 = moduleMethod33;
        new ModuleMethod(frame4, 35, (Object) null, 0);
        lambda$Fn17 = moduleMethod34;
        new ModuleMethod(frame4, 36, (Object) null, 0);
        lambda$Fn18 = moduleMethod35;
        new ModuleMethod(frame4, 37, (Object) null, 0);
        lambda$Fn19 = moduleMethod36;
        new ModuleMethod(frame4, 38, (Object) null, 0);
        lambda$Fn20 = moduleMethod37;
        new ModuleMethod(frame4, 39, (Object) null, 0);
        lambda$Fn21 = moduleMethod38;
        new ModuleMethod(frame4, 40, (Object) null, 0);
        lambda$Fn22 = moduleMethod39;
        new ModuleMethod(frame4, 41, (Object) null, 0);
        lambda$Fn23 = moduleMethod40;
        new ModuleMethod(frame4, 42, Lit114, 0);
        this.Marker7$Click = moduleMethod41;
        new ModuleMethod(frame4, 43, (Object) null, 0);
        lambda$Fn24 = moduleMethod42;
        new ModuleMethod(frame4, 44, (Object) null, 0);
        lambda$Fn25 = moduleMethod43;
        new ModuleMethod(frame4, 45, (Object) null, 0);
        lambda$Fn26 = moduleMethod44;
        new ModuleMethod(frame4, 46, (Object) null, 0);
        lambda$Fn27 = moduleMethod45;
        new ModuleMethod(frame4, 47, (Object) null, 0);
        lambda$Fn28 = moduleMethod46;
        new ModuleMethod(frame4, 48, (Object) null, 0);
        lambda$Fn29 = moduleMethod47;
        new ModuleMethod(frame4, 49, (Object) null, 0);
        lambda$Fn30 = moduleMethod48;
        new ModuleMethod(frame4, 50, (Object) null, 0);
        lambda$Fn31 = moduleMethod49;
        new ModuleMethod(frame4, 51, (Object) null, 0);
        lambda$Fn32 = moduleMethod50;
        new ModuleMethod(frame4, 52, (Object) null, 0);
        lambda$Fn33 = moduleMethod51;
        new ModuleMethod(frame4, 53, (Object) null, 0);
        lambda$Fn34 = moduleMethod52;
        new ModuleMethod(frame4, 54, (Object) null, 0);
        lambda$Fn35 = moduleMethod53;
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        Throwable th;
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th2) {
            th = th2;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        String obj;
        Object obj2;
        Consumer $result = $ctx.consumer;
        C1234runtime.$instance.run();
        this.$Stdebug$Mnform$St = Boolean.FALSE;
        this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
        Object[] objArr = new Object[2];
        objArr[0] = misc.symbol$To$String(Lit0);
        Object[] objArr2 = objArr;
        objArr2[1] = "-global-vars";
        FString stringAppend = strings.stringAppend(objArr2);
        FString fString = stringAppend;
        if (stringAppend == null) {
            obj = null;
        } else {
            obj = fString.toString();
        }
        this.global$Mnvar$Mnenvironment = Environment.make(obj);
        Screen1 = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        C1234runtime.$instance.run();
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addGlobalVarToCurrentFormEnvironment(Lit3, C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list4(C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/556623570705580033/eb49176de17b2ab0caba055a408f4ef4.png?size=128", "Maria Cristina", "Negócios"), Lit4, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/697490228692254731/d87d53408aa03072289872e5a599cef3.png?size=128", "Rodrigo Narcizo", "Negócios"), Lit5, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/736024002728034324/09e5c2e5c5330b8865edb7d686715e93.png?size=128", "Samirian Grimberg", "Negócios"), Lit6, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/696874762106044427/7bcc3862feee6987cd5c667499670870.png?size=128", "Uandisson Miranda", "Desenvolvedor"), Lit7, "make a list")), Lit8, "make a list")), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addGlobalVarToCurrentFormEnvironment(Lit14, C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list4(C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://image.flaticon.com/icons/png/512/1986/1986987.png", "Nível 10 - amigo de ciclistas", "Próximo nível: 100 ciclistas "), Lit15, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/Users-icon.png", "Ciclistas amigos", "50 \nvidas salvas de possíveis acidentes!"), Lit16, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://www.iconpacks.net/icons/1/free-coin-icon-794-thumb.png", "Desconto acumulado no IPVA", "R$ 100,00"), Lit17, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://upload.wikimedia.org/wikipedia/commons/8/88/Red_triangle_alert_icon.png", "Alertas de proximidade", "5 emitidos"), Lit18, "make a list")), Lit19, "make a list")), $result);
        } else {
            addToGlobalVars(Lit14, lambda$Fn3);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addGlobalVarToCurrentFormEnvironment(Lit25, C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list4(C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Maria Crstina", "Negócios"), Lit26, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Rodrigo Narcizo", "Negócios"), Lit27, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Samirian Grimberg", "Negócios"), Lit28, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Uandisson Miranda", "Desenvolvedor"), Lit29, "make a list")), Lit30, "make a list")), $result);
        } else {
            addToGlobalVars(Lit25, lambda$Fn4);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit36, Lit37, Lit38);
            Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit39, "5731346200657920", Lit40);
            Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit41, "Tagline", Lit40);
            Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit42, "br.com.tagline", Lit40);
            Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit43, "AppTheme.Light.DarkActionBar", Lit40);
            Values.writeValues(C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit44, "Projeto TagLine", Lit40), $result);
        } else {
            new Promise(lambda$Fn5);
            addToFormDoAfterCreation(obj2);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Object addToCurrentFormEnvironment = C1234runtime.addToCurrentFormEnvironment(Lit59, this.Screen1$Initialize);
        } else {
            addToFormEnvironment(Lit59, this.Screen1$Initialize);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) C1234runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
        } else {
            addToEvents(Lit0, Lit60);
        }
        this.View_Pager1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit61, Lit45, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit62, Lit45, Boolean.FALSE);
        }
        this.Map1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit63, Lit47, lambda$Fn8), $result);
        } else {
            addToComponents(Lit0, Lit72, Lit47, lambda$Fn9);
        }
        this.Marker1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit73, Lit74, lambda$Fn10), $result);
        } else {
            addToComponents(Lit47, Lit80, Lit74, lambda$Fn11);
        }
        this.Marker2 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit81, Lit82, lambda$Fn12), $result);
        } else {
            addToComponents(Lit47, Lit85, Lit82, lambda$Fn13);
        }
        this.Marker3 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit86, Lit87, lambda$Fn14), $result);
        } else {
            addToComponents(Lit47, Lit90, Lit87, lambda$Fn15);
        }
        this.Marker4 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit91, Lit92, lambda$Fn16), $result);
        } else {
            addToComponents(Lit47, Lit95, Lit92, lambda$Fn17);
        }
        this.Marker5 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit96, Lit97, lambda$Fn18), $result);
        } else {
            addToComponents(Lit47, Lit100, Lit97, lambda$Fn19);
        }
        this.Marker6 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit101, Lit102, lambda$Fn20), $result);
        } else {
            addToComponents(Lit47, Lit105, Lit102, lambda$Fn21);
        }
        this.Marker7 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit47, Lit106, Lit107, lambda$Fn22), $result);
        } else {
            addToComponents(Lit47, Lit110, Lit107, lambda$Fn23);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Object addToCurrentFormEnvironment2 = C1234runtime.addToCurrentFormEnvironment(Lit114, this.Marker7$Click);
        } else {
            addToFormEnvironment(Lit114, this.Marker7$Click);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) C1234runtime.$Stthis$Mnform$St, "Marker7", "Click");
        } else {
            addToEvents(Lit107, Lit115);
        }
        this.Horizontal_Arrangement1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit116, Lit49, lambda$Fn24), $result);
        } else {
            addToComponents(Lit0, Lit119, Lit49, lambda$Fn25);
        }
        this.List_View_Image_and_Text1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit49, Lit120, Lit57, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit49, Lit121, Lit57, Boolean.FALSE);
        }
        this.Horizontal_Arrangement2 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit122, Lit51, lambda$Fn26), $result);
        } else {
            addToComponents(Lit0, Lit124, Lit51, lambda$Fn27);
        }
        this.Vertical_Arrangement1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit51, Lit125, Lit126, lambda$Fn28), $result);
        } else {
            addToComponents(Lit51, Lit127, Lit126, lambda$Fn29);
        }
        this.Label1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit126, Lit128, Lit129, lambda$Fn30), $result);
        } else {
            addToComponents(Lit126, Lit136, Lit129, lambda$Fn31);
        }
        this.List_View_Image_and_Text4 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit126, Lit137, Lit53, lambda$Fn32), $result);
        } else {
            addToComponents(Lit126, Lit144, Lit53, lambda$Fn33);
        }
        this.Notifier1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit145, Lit111, lambda$Fn34), $result);
        } else {
            addToComponents(Lit0, Lit152, Lit111, lambda$Fn35);
        }
        this.Push_Notifications1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit153, Lit154, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit155, Lit154, Boolean.FALSE);
        }
        C1234runtime.initRuntime();
    }

    static Object lambda3() {
        return C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list4(C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/556623570705580033/eb49176de17b2ab0caba055a408f4ef4.png?size=128", "Maria Cristina", "Negócios"), Lit9, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/697490228692254731/d87d53408aa03072289872e5a599cef3.png?size=128", "Rodrigo Narcizo", "Negócios"), Lit10, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/736024002728034324/09e5c2e5c5330b8865edb7d686715e93.png?size=128", "Samirian Grimberg", "Negócios"), Lit11, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://cdn.discordapp.com/avatars/696874762106044427/7bcc3862feee6987cd5c667499670870.png?size=128", "Uandisson Miranda", "Desenvolvedor"), Lit12, "make a list")), Lit13, "make a list");
    }

    static Object lambda4() {
        return C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list4(C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://image.flaticon.com/icons/png/512/1986/1986987.png", "Nível 10 - amigo de ciclistas", "Próximo nível: 100 ciclistas "), Lit20, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/Users-icon.png", "Ciclistas amigos", "50 \nvidas salvas de possíveis acidentes!"), Lit21, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://www.iconpacks.net/icons/1/free-coin-icon-794-thumb.png", "Desconto acumulado no IPVA", "R$ 100,00"), Lit22, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list3("https://upload.wikimedia.org/wikipedia/commons/8/88/Red_triangle_alert_icon.png", "Alertas de proximidade", "5 emitidos"), Lit23, "make a list")), Lit24, "make a list");
    }

    static Object lambda5() {
        return C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list4(C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Maria Crstina", "Negócios"), Lit31, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Rodrigo Narcizo", "Negócios"), Lit32, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Samirian Grimberg", "Negócios"), Lit33, "make a list"), C1234runtime.callYailPrimitive(C1234runtime.make$Mnyail$Mnlist, LList.list2("Uandisson Miranda", "Desenvolvedor"), Lit34, "make a list")), Lit35, "make a list");
    }

    static Object lambda6() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit36, Lit37, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit39, "5731346200657920", Lit40);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit41, "Tagline", Lit40);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit42, "br.com.tagline", Lit40);
        Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit43, "AppTheme.Light.DarkActionBar", Lit40);
        return C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit44, "Projeto TagLine", Lit40);
    }

    public Object Screen1$Initialize() {
        C1234runtime.setThisForm();
        Object callComponentMethod = C1234runtime.callComponentMethod(Lit45, Lit46, LList.list2(C1234runtime.lookupInCurrentFormEnvironment(Lit47), "Mapa"), Lit48);
        Object callComponentMethod2 = C1234runtime.callComponentMethod(Lit45, Lit46, LList.list2(C1234runtime.lookupInCurrentFormEnvironment(Lit49), "Dashboard"), Lit50);
        Object callComponentMethod3 = C1234runtime.callComponentMethod(Lit45, Lit46, LList.list2(C1234runtime.lookupInCurrentFormEnvironment(Lit51), "Sobre"), Lit52);
        ModuleMethod moduleMethod = proc$Fn6;
        Object yailForEach = C1234runtime.yailForEach(proc$Fn6, C1234runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, C1234runtime.$Stthe$Mnnull$Mnvalue$St));
        Procedure proc = proc$Fn7;
        return C1234runtime.yailForEach(proc$Fn7, C1234runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, C1234runtime.$Stthe$Mnnull$Mnvalue$St));
    }

    public static Object lambda7proc(Object obj) {
        Object obj2;
        Object $item = obj;
        SimpleSymbol simpleSymbol = Lit53;
        SimpleSymbol simpleSymbol2 = Lit54;
        if ($item instanceof Package) {
            Object[] objArr = new Object[3];
            objArr[0] = "The variable ";
            Object[] objArr2 = objArr;
            objArr2[1] = C1234runtime.getDisplayRepresentation(Lit55);
            Object[] objArr3 = objArr2;
            objArr3[2] = " is not bound in the current context";
            obj2 = C1234runtime.signalRuntimeError(strings.stringAppend(objArr3), "Unbound Variable");
        } else {
            obj2 = $item;
        }
        return C1234runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list1(obj2), Lit56);
    }

    public static Object lambda8proc(Object obj) {
        Object obj2;
        Object $item = obj;
        SimpleSymbol simpleSymbol = Lit57;
        SimpleSymbol simpleSymbol2 = Lit54;
        if ($item instanceof Package) {
            Object[] objArr = new Object[3];
            objArr[0] = "The variable ";
            Object[] objArr2 = objArr;
            objArr2[1] = C1234runtime.getDisplayRepresentation(Lit55);
            Object[] objArr3 = objArr2;
            objArr3[2] = " is not bound in the current context";
            obj2 = C1234runtime.signalRuntimeError(strings.stringAppend(objArr3), "Unbound Variable");
        } else {
            obj2 = $item;
        }
        return C1234runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list1(obj2), Lit58);
    }

    static Object lambda10() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit64, "-8.05428, -34.8813", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit65, Lit66, Lit38);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit67, Lit66, Lit38);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit68, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit70, Boolean.TRUE, Lit69);
        return C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit71, Boolean.TRUE, Lit69);
    }

    static Object lambda9() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit64, "-8.05428, -34.8813", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit65, Lit66, Lit38);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit67, Lit66, Lit38);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit68, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit70, Boolean.TRUE, Lit69);
        return C1234runtime.setAndCoerceProperty$Ex(Lit47, Lit71, Boolean.TRUE, Lit69);
    }

    static Object lambda11() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit74, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit74, Lit76, Lit77, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit74, Lit78, Lit79, Lit38);
    }

    static Object lambda12() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit74, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit74, Lit76, Lit77, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit74, Lit78, Lit79, Lit38);
    }

    static Object lambda13() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit82, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit82, Lit76, Lit83, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit82, Lit78, Lit84, Lit38);
    }

    static Object lambda14() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit82, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit82, Lit76, Lit83, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit82, Lit78, Lit84, Lit38);
    }

    static Object lambda15() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit87, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit87, Lit76, Lit88, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit87, Lit78, Lit89, Lit38);
    }

    static Object lambda16() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit87, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit87, Lit76, Lit88, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit87, Lit78, Lit89, Lit38);
    }

    static Object lambda17() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit92, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit92, Lit76, Lit93, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit92, Lit78, Lit94, Lit38);
    }

    static Object lambda18() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit92, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit92, Lit76, Lit93, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit92, Lit78, Lit94, Lit38);
    }

    static Object lambda19() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit97, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit97, Lit76, Lit98, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit97, Lit78, Lit99, Lit38);
    }

    static Object lambda20() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit97, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit97, Lit76, Lit98, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit97, Lit78, Lit99, Lit38);
    }

    static Object lambda21() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit102, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit102, Lit76, Lit103, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit102, Lit78, Lit104, Lit38);
    }

    static Object lambda22() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit102, Lit75, "bicicleta-de-estrada_(1).png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit102, Lit76, Lit103, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit102, Lit78, Lit104, Lit38);
    }

    static Object lambda23() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit107, Lit75, "carro.png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit107, Lit76, Lit108, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit107, Lit78, Lit109, Lit38);
    }

    static Object lambda24() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit107, Lit75, "carro.png", Lit40);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit107, Lit76, Lit108, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit107, Lit78, Lit109, Lit38);
    }

    public Object Marker7$Click() {
        C1234runtime.setThisForm();
        return C1234runtime.callComponentMethod(Lit111, Lit112, LList.list3("PERIGO MUITO PRÓXIMO!!!", "Alerta!", "Ok"), Lit113);
    }

    static Object lambda25() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit49, Lit117, Lit118, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit49, Lit65, Lit66, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit49, Lit67, Lit66, Lit38);
    }

    static Object lambda26() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit49, Lit117, Lit118, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit49, Lit65, Lit66, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit49, Lit67, Lit66, Lit38);
    }

    static Object lambda27() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit51, Lit117, Lit123, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit51, Lit65, Lit66, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit51, Lit67, Lit66, Lit38);
    }

    static Object lambda28() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit51, Lit117, Lit123, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit51, Lit65, Lit66, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit51, Lit67, Lit66, Lit38);
    }

    static Object lambda29() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit126, Lit65, Lit66, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit126, Lit67, Lit66, Lit38);
    }

    static Object lambda30() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit126, Lit65, Lit66, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit126, Lit67, Lit66, Lit38);
    }

    static Object lambda31() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit130, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit131, Lit132, Lit38);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit67, Lit66, Lit38);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit133, "Projeto TagLine - Time Hack.gov.PE", Lit40);
        return C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit134, Lit135, Lit38);
    }

    static Object lambda32() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit130, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit131, Lit132, Lit38);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit67, Lit66, Lit38);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit133, "Projeto TagLine - Time Hack.gov.PE", Lit40);
        return C1234runtime.setAndCoerceProperty$Ex(Lit129, Lit134, Lit135, Lit38);
    }

    static Object lambda33() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit138, Lit139, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit140, Lit141, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit142, Lit143, Lit38);
    }

    static Object lambda34() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit138, Lit139, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit140, Lit141, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit142, Lit143, Lit38);
    }

    static Object lambda35() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit117, Lit146, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit147, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit148, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit149, Lit150, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit151, Boolean.FALSE, Lit69);
    }

    static Object lambda36() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit117, Lit146, Lit38);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit147, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit148, Boolean.TRUE, Lit69);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit149, Lit150, Lit38);
        return C1234runtime.setAndCoerceProperty$Ex(Lit111, Lit151, Boolean.FALSE, Lit69);
    }

    /* renamed from: io.kodular.tagline.Tagline.Screen1$frame */
    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main;

        public frame() {
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            ModuleMethod moduleMethod2 = moduleMethod;
            Object obj2 = obj;
            CallContext callContext2 = callContext;
            switch (moduleMethod2.selector) {
                case 1:
                    callContext2.value1 = obj2;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 2:
                    callContext2.value1 = obj2;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 4:
                    CallContext callContext3 = callContext2;
                    Object obj3 = obj2;
                    Object obj4 = obj3;
                    if (!(obj3 instanceof Symbol)) {
                        return -786431;
                    }
                    callContext3.value1 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 6:
                    CallContext callContext4 = callContext2;
                    Object obj5 = obj2;
                    Object obj6 = obj5;
                    if (!(obj5 instanceof Symbol)) {
                        return -786431;
                    }
                    callContext4.value1 = obj6;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 11:
                    callContext2.value1 = obj2;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 12:
                    callContext2.value1 = obj2;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 13:
                    CallContext callContext5 = callContext2;
                    Object obj7 = obj2;
                    Object obj8 = obj7;
                    if (!(obj7 instanceof Screen1)) {
                        return -786431;
                    }
                    callContext5.value1 = obj8;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 23:
                    callContext2.value1 = obj2;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                case 24:
                    callContext2.value1 = obj2;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod2, obj2, callContext2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            ModuleMethod moduleMethod2 = moduleMethod;
            Object obj3 = obj;
            Object obj4 = obj2;
            CallContext callContext2 = callContext;
            switch (moduleMethod2.selector) {
                case 3:
                    CallContext callContext3 = callContext2;
                    Object obj5 = obj3;
                    Object obj6 = obj5;
                    if (!(obj5 instanceof Symbol)) {
                        return -786431;
                    }
                    callContext3.value1 = obj6;
                    callContext2.value2 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 2;
                    return 0;
                case 4:
                    CallContext callContext4 = callContext2;
                    Object obj7 = obj3;
                    Object obj8 = obj7;
                    if (!(obj7 instanceof Symbol)) {
                        return -786431;
                    }
                    callContext4.value1 = obj8;
                    callContext2.value2 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 2;
                    return 0;
                case 7:
                    CallContext callContext5 = callContext2;
                    Object obj9 = obj3;
                    Object obj10 = obj9;
                    if (!(obj9 instanceof Symbol)) {
                        return -786431;
                    }
                    callContext5.value1 = obj10;
                    callContext2.value2 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 2;
                    return 0;
                case 8:
                    callContext2.value1 = obj3;
                    callContext2.value2 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 2;
                    return 0;
                case 10:
                    callContext2.value1 = obj3;
                    callContext2.value2 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 2;
                    return 0;
                case 16:
                    callContext2.value1 = obj3;
                    callContext2.value2 = obj4;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod2, obj3, obj4, callContext2);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            ModuleMethod moduleMethod2 = moduleMethod;
            Object obj5 = obj;
            Object obj6 = obj2;
            Object obj7 = obj3;
            Object obj8 = obj4;
            CallContext callContext2 = callContext;
            switch (moduleMethod2.selector) {
                case 9:
                    callContext2.value1 = obj5;
                    callContext2.value2 = obj6;
                    callContext2.value3 = obj7;
                    callContext2.value4 = obj8;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 4;
                    return 0;
                case 14:
                    CallContext callContext3 = callContext2;
                    Object obj9 = obj5;
                    Object obj10 = obj9;
                    if (!(obj9 instanceof Screen1)) {
                        return -786431;
                    }
                    callContext3.value1 = obj10;
                    CallContext callContext4 = callContext2;
                    Object obj11 = obj6;
                    Object obj12 = obj11;
                    if (!(obj11 instanceof Component)) {
                        return -786430;
                    }
                    callContext4.value2 = obj12;
                    CallContext callContext5 = callContext2;
                    Object obj13 = obj7;
                    Object obj14 = obj13;
                    if (!(obj13 instanceof String)) {
                        return -786429;
                    }
                    callContext5.value3 = obj14;
                    CallContext callContext6 = callContext2;
                    Object obj15 = obj8;
                    Object obj16 = obj15;
                    if (!(obj15 instanceof String)) {
                        return -786428;
                    }
                    callContext6.value4 = obj16;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 4;
                    return 0;
                case 15:
                    CallContext callContext7 = callContext2;
                    Object obj17 = obj5;
                    Object obj18 = obj17;
                    if (!(obj17 instanceof Screen1)) {
                        return -786431;
                    }
                    callContext7.value1 = obj18;
                    CallContext callContext8 = callContext2;
                    Object obj19 = obj6;
                    Object obj20 = obj19;
                    if (!(obj19 instanceof Component)) {
                        return -786430;
                    }
                    callContext8.value2 = obj20;
                    CallContext callContext9 = callContext2;
                    Object obj21 = obj7;
                    Object obj22 = obj21;
                    if (!(obj21 instanceof String)) {
                        return -786429;
                    }
                    callContext9.value3 = obj22;
                    CallContext callContext10 = callContext2;
                    Object obj23 = obj8;
                    Object obj24 = obj23;
                    Object obj25 = obj23;
                    if (1 == 0) {
                        return -786428;
                    }
                    callContext10.value4 = obj24;
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod2, obj5, obj6, obj7, obj8, callContext2);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            Throwable th;
            Throwable th2;
            ModuleMethod moduleMethod2 = moduleMethod;
            Object obj2 = obj;
            switch (moduleMethod2.selector) {
                case 1:
                    return this.$main.getSimpleName(obj2);
                case 2:
                    this.$main.androidLogForm(obj2);
                    return Values.empty;
                case 4:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj2);
                    } catch (ClassCastException e) {
                        ClassCastException classCastException = e;
                        Throwable th3 = th2;
                        new WrongType(classCastException, "lookup-in-form-environment", 1, obj2);
                        throw th3;
                    }
                case 6:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2) {
                        ClassCastException classCastException2 = e2;
                        Throwable th4 = th;
                        new WrongType(classCastException2, "is-bound-in-form-environment", 1, obj2);
                        throw th4;
                    }
                case 11:
                    this.$main.addToFormDoAfterCreation(obj2);
                    return Values.empty;
                case 12:
                    this.$main.sendError(obj2);
                    return Values.empty;
                case 13:
                    this.$main.processException(obj2);
                    return Values.empty;
                case 23:
                    return Screen1.lambda7proc(obj2);
                case 24:
                    return Screen1.lambda8proc(obj2);
                default:
                    return super.apply1(moduleMethod2, obj2);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            Throwable th;
            Throwable th2;
            Throwable th3;
            Throwable th4;
            Throwable th5;
            Throwable th6;
            Throwable th7;
            Throwable th8;
            ModuleMethod moduleMethod2 = moduleMethod;
            Object obj5 = obj;
            Object obj6 = obj2;
            Object obj7 = obj3;
            Object obj8 = obj4;
            switch (moduleMethod2.selector) {
                case 9:
                    this.$main.addToComponents(obj5, obj6, obj7, obj8);
                    return Values.empty;
                case 14:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj5, (String) obj6, (String) obj7, (Object[]) obj8) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    ClassCastException classCastException = e;
                                    Throwable th9 = th8;
                                    new WrongType(classCastException, "dispatchEvent", 4, obj8);
                                    throw th9;
                                }
                            } catch (ClassCastException e2) {
                                ClassCastException classCastException2 = e2;
                                Throwable th10 = th7;
                                new WrongType(classCastException2, "dispatchEvent", 3, obj7);
                                throw th10;
                            }
                        } catch (ClassCastException e3) {
                            ClassCastException classCastException3 = e3;
                            Throwable th11 = th6;
                            new WrongType(classCastException3, "dispatchEvent", 2, obj6);
                            throw th11;
                        }
                    } catch (ClassCastException e4) {
                        ClassCastException classCastException4 = e4;
                        Throwable th12 = th5;
                        new WrongType(classCastException4, "dispatchEvent", 1, obj5);
                        throw th12;
                    }
                case 15:
                    try {
                        try {
                            try {
                                try {
                                    this.$main.dispatchGenericEvent((Component) obj5, (String) obj6, obj7 != Boolean.FALSE, (Object[]) obj8);
                                    return Values.empty;
                                } catch (ClassCastException e5) {
                                    ClassCastException classCastException5 = e5;
                                    Throwable th13 = th4;
                                    new WrongType(classCastException5, "dispatchGenericEvent", 4, obj8);
                                    throw th13;
                                }
                            } catch (ClassCastException e6) {
                                ClassCastException classCastException6 = e6;
                                Throwable th14 = th3;
                                new WrongType(classCastException6, "dispatchGenericEvent", 3, obj7);
                                throw th14;
                            }
                        } catch (ClassCastException e7) {
                            ClassCastException classCastException7 = e7;
                            Throwable th15 = th2;
                            new WrongType(classCastException7, "dispatchGenericEvent", 2, obj6);
                            throw th15;
                        }
                    } catch (ClassCastException e8) {
                        ClassCastException classCastException8 = e8;
                        Throwable th16 = th;
                        new WrongType(classCastException8, "dispatchGenericEvent", 1, obj5);
                        throw th16;
                    }
                default:
                    return super.apply4(moduleMethod2, obj5, obj6, obj7, obj8);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            Throwable th;
            Throwable th2;
            Throwable th3;
            ModuleMethod moduleMethod2 = moduleMethod;
            Object obj3 = obj;
            Object obj4 = obj2;
            switch (moduleMethod2.selector) {
                case 3:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj3, obj4);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        ClassCastException classCastException = e;
                        Throwable th4 = th3;
                        new WrongType(classCastException, "add-to-form-environment", 1, obj3);
                        throw th4;
                    }
                case 4:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj3, obj4);
                    } catch (ClassCastException e2) {
                        ClassCastException classCastException2 = e2;
                        Throwable th5 = th2;
                        new WrongType(classCastException2, "lookup-in-form-environment", 1, obj3);
                        throw th5;
                    }
                case 7:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj3, obj4);
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        ClassCastException classCastException3 = e3;
                        Throwable th6 = th;
                        new WrongType(classCastException3, "add-to-global-var-environment", 1, obj3);
                        throw th6;
                    }
                case 8:
                    this.$main.addToEvents(obj3, obj4);
                    return Values.empty;
                case 10:
                    this.$main.addToGlobalVars(obj3, obj4);
                    return Values.empty;
                case 16:
                    return this.$main.lookupHandler(obj3, obj4);
                default:
                    return super.apply2(moduleMethod2, obj3, obj4);
            }
        }

        public Object apply0(ModuleMethod moduleMethod) {
            ModuleMethod moduleMethod2 = moduleMethod;
            switch (moduleMethod2.selector) {
                case 17:
                    return Screen1.lambda2();
                case 18:
                    this.$main.$define();
                    return Values.empty;
                case 19:
                    return Screen1.lambda3();
                case 20:
                    return Screen1.lambda4();
                case 21:
                    return Screen1.lambda5();
                case 22:
                    return Screen1.lambda6();
                case 25:
                    return this.$main.Screen1$Initialize();
                case 26:
                    return Screen1.lambda9();
                case 27:
                    return Screen1.lambda10();
                case 28:
                    return Screen1.lambda11();
                case 29:
                    return Screen1.lambda12();
                case 30:
                    return Screen1.lambda13();
                case 31:
                    return Screen1.lambda14();
                case 32:
                    return Screen1.lambda15();
                case 33:
                    return Screen1.lambda16();
                case 34:
                    return Screen1.lambda17();
                case 35:
                    return Screen1.lambda18();
                case 36:
                    return Screen1.lambda19();
                case 37:
                    return Screen1.lambda20();
                case 38:
                    return Screen1.lambda21();
                case 39:
                    return Screen1.lambda22();
                case 40:
                    return Screen1.lambda23();
                case 41:
                    return Screen1.lambda24();
                case 42:
                    return this.$main.Marker7$Click();
                case 43:
                    return Screen1.lambda25();
                case 44:
                    return Screen1.lambda26();
                case 45:
                    return Screen1.lambda27();
                case 46:
                    return Screen1.lambda28();
                case 47:
                    return Screen1.lambda29();
                case 48:
                    return Screen1.lambda30();
                case 49:
                    return Screen1.lambda31();
                case 50:
                    return Screen1.lambda32();
                case 51:
                    return Screen1.lambda33();
                case 52:
                    return Screen1.lambda34();
                case 53:
                    return Screen1.lambda35();
                case 54:
                    return Screen1.lambda36();
                default:
                    return super.apply0(moduleMethod2);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            ModuleMethod moduleMethod2 = moduleMethod;
            CallContext callContext2 = callContext;
            switch (moduleMethod2.selector) {
                case 17:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 18:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 19:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 20:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 21:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 22:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 25:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 26:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 27:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 28:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 29:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 30:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 31:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 32:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 33:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 34:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 35:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 36:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 37:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 38:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 39:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 40:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 41:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 42:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 43:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 44:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 45:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 46:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 47:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 48:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 49:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 50:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 51:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 52:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 53:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 54:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod2, callContext2);
            }
        }
    }

    public String getSimpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol symbol, Object obj) {
        Symbol name = symbol;
        Object object = obj;
        Object[] objArr = new Object[4];
        objArr[0] = "Adding ~A to env ~A with value ~A";
        Object[] objArr2 = objArr;
        objArr2[1] = name;
        Object[] objArr3 = objArr2;
        objArr3[2] = this.form$Mnenvironment;
        Object[] objArr4 = objArr3;
        objArr4[3] = object;
        androidLogForm(Format.formatToString(0, objArr4));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol symbol, Object obj) {
        Object obj2;
        Symbol name = symbol;
        Object default$Mnvalue = obj;
        boolean x = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & true;
        if (!x ? !x : !this.form$Mnenvironment.isBound(name)) {
            obj2 = default$Mnvalue;
        } else {
            obj2 = this.form$Mnenvironment.get(name);
        }
        return obj2;
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol symbol, Object obj) {
        Symbol name = symbol;
        Object object = obj;
        Object[] objArr = new Object[4];
        objArr[0] = "Adding ~A to env ~A with value ~A";
        Object[] objArr2 = objArr;
        objArr2[1] = name;
        Object[] objArr3 = objArr2;
        objArr3[2] = this.global$Mnvar$Mnenvironment;
        Object[] objArr4 = objArr3;
        objArr4[3] = object;
        androidLogForm(Format.formatToString(0, objArr4));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = C1259lists.cons(C1259lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = C1259lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = C1259lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = C1259lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        Object obj = error;
        RetValManager.sendError(obj == null ? null : obj.toString());
    }

    public void processException(Object obj) {
        Object ex = obj;
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    public boolean dispatchEvent(Component component, String str, String str2, Object[] objArr) {
        boolean z;
        boolean z2;
        Component componentObject = component;
        String registeredComponentName = str;
        String eventName = str2;
        Object[] args = objArr;
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            z = false;
        } else if (lookupInFormEnvironment(registeredObject) == componentObject) {
            try {
                Object apply2 = Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                z2 = true;
            } catch (PermissionException e) {
                PermissionException exception = e;
                exception.printStackTrace();
                boolean x = this == componentObject;
                if (!x ? !x : !IsEqual.apply(eventName, "PermissionNeeded")) {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                } else {
                    processException(exception);
                }
                z2 = false;
            } catch (Throwable th) {
                Throwable exception2 = th;
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
                z2 = false;
            }
            z = z2;
        } else {
            z = false;
        }
        return z;
    }

    public void dispatchGenericEvent(Component component, String str, boolean z, Object[] objArr) {
        Boolean bool;
        Component componentObject = component;
        String eventName = str;
        boolean notAlreadyHandled = z;
        Object[] args = objArr;
        Object[] objArr2 = new Object[4];
        objArr2[0] = "any$";
        Object[] objArr3 = objArr2;
        objArr3[1] = getSimpleName(componentObject);
        Object[] objArr4 = objArr3;
        objArr4[2] = "$";
        Object[] objArr5 = objArr4;
        objArr5[3] = eventName;
        Object handler = lookupInFormEnvironment(misc.string$To$Symbol(strings.stringAppend(objArr5)));
        if (handler != Boolean.FALSE) {
            try {
                Apply apply = Scheme.apply;
                Object obj = handler;
                Component component2 = componentObject;
                if (notAlreadyHandled) {
                    bool = Boolean.TRUE;
                } else {
                    bool = Boolean.FALSE;
                }
                Object apply2 = apply.apply2(obj, C1259lists.cons(component2, C1259lists.cons(bool, LList.makeList(args, 0))));
            } catch (PermissionException e) {
                PermissionException exception = e;
                exception.printStackTrace();
                boolean x = this == componentObject;
                if (!x ? !x : !IsEqual.apply(eventName, "PermissionNeeded")) {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                } else {
                    processException(exception);
                }
            } catch (Throwable th) {
                Throwable exception2 = th;
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
            }
        }
    }

    public Object lookupHandler(Object componentName, Object obj) {
        Object eventName = obj;
        Object obj2 = componentName;
        String obj3 = obj2 == null ? null : obj2.toString();
        Object obj4 = eventName;
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj3, obj4 == null ? null : obj4.toString())));
    }

    public void $define() {
        Object obj;
        Throwable th;
        Object obj2;
        Throwable th2;
        Object obj3;
        Throwable th3;
        Object obj4;
        Throwable th4;
        Object obj5;
        Throwable th5;
        Object obj6;
        Throwable th6;
        Object obj7;
        Throwable th7;
        Object obj8;
        Throwable th8;
        Throwable th9;
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception e) {
            Exception exception = e;
            androidLogForm(exception.getMessage());
            processException(exception);
        }
        Screen1 = this;
        addToFormEnvironment(Lit0, this);
        Object obj9 = this.events$Mnto$Mnregister;
        while (true) {
            Object obj10 = obj9;
            if (obj10 == LList.Empty) {
                break;
            }
            Object obj11 = obj10;
            Object obj12 = obj11;
            try {
                Pair arg0 = (Pair) obj11;
                Object event$Mninfo = arg0.getCar();
                Object apply1 = C1259lists.car.apply1(event$Mninfo);
                String obj13 = apply1 == null ? null : apply1.toString();
                Object apply12 = C1259lists.cdr.apply1(event$Mninfo);
                EventDispatcher.registerEventForDelegation(this, obj13, apply12 == null ? null : apply12.toString());
                obj9 = arg0.getCdr();
            } catch (ClassCastException e2) {
                ClassCastException classCastException = e2;
                Throwable th10 = th9;
                new WrongType(classCastException, "arg0", -2, obj12);
                throw th10;
            }
        }
        try {
            LList components = C1259lists.reverse(this.components$Mnto$Mncreate);
            addToGlobalVars(Lit2, lambda$Fn1);
            LList event$Mninfo2 = components;
            while (event$Mninfo2 != LList.Empty) {
                Object obj14 = event$Mninfo2;
                obj6 = obj14;
                Pair arg02 = (Pair) obj14;
                Object component$Mninfo = arg02.getCar();
                Object apply13 = C1259lists.caddr.apply1(component$Mninfo);
                Object apply14 = C1259lists.cadddr.apply1(component$Mninfo);
                Object component$Mntype = C1259lists.cadr.apply1(component$Mninfo);
                Object apply15 = C1259lists.car.apply1(component$Mninfo);
                obj7 = apply15;
                Object component$Mnname = apply13;
                Object component$Mnobject = Invoke.make.apply2(component$Mntype, lookupInFormEnvironment((Symbol) apply15));
                Object apply3 = SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
                Object obj15 = component$Mnname;
                obj8 = obj15;
                addToFormEnvironment((Symbol) obj15, component$Mnobject);
                event$Mninfo2 = arg02.getCdr();
            }
            LList reverse = C1259lists.reverse(this.global$Mnvars$Mnto$Mncreate);
            while (reverse != LList.Empty) {
                Object obj16 = reverse;
                obj4 = obj16;
                Pair arg03 = (Pair) obj16;
                Object var$Mnval = arg03.getCar();
                Object apply16 = C1259lists.car.apply1(var$Mnval);
                obj5 = apply16;
                addToGlobalVarEnvironment((Symbol) apply16, Scheme.applyToArgs.apply1(C1259lists.cadr.apply1(var$Mnval)));
                reverse = arg03.getCdr();
            }
            Object reverse2 = C1259lists.reverse(this.form$Mndo$Mnafter$Mncreation);
            while (reverse2 != LList.Empty) {
                Object obj17 = reverse2;
                obj3 = obj17;
                Pair arg04 = (Pair) obj17;
                Object force = misc.force(arg04.getCar());
                reverse2 = arg04.getCdr();
            }
            LList component$Mndescriptors = components;
            LList lList = component$Mndescriptors;
            while (lList != LList.Empty) {
                Object obj18 = lList;
                obj2 = obj18;
                Pair arg05 = (Pair) obj18;
                Object component$Mninfo2 = arg05.getCar();
                Object apply17 = C1259lists.caddr.apply1(component$Mninfo2);
                Object init$Mnthunk = C1259lists.cadddr.apply1(component$Mninfo2);
                if (init$Mnthunk != Boolean.FALSE) {
                    Object apply18 = Scheme.applyToArgs.apply1(init$Mnthunk);
                }
                lList = arg05.getCdr();
            }
            LList lList2 = component$Mndescriptors;
            while (lList2 != LList.Empty) {
                Object obj19 = lList2;
                obj = obj19;
                Pair arg06 = (Pair) obj19;
                Object component$Mninfo3 = arg06.getCar();
                Object component$Mnname2 = C1259lists.caddr.apply1(component$Mninfo3);
                Object apply19 = C1259lists.cadddr.apply1(component$Mninfo3);
                callInitialize(SlotGet.field.apply2(this, component$Mnname2));
                lList2 = arg06.getCdr();
            }
        } catch (ClassCastException e3) {
            ClassCastException classCastException2 = e3;
            Throwable th11 = th;
            new WrongType(classCastException2, "arg0", -2, obj);
            throw th11;
        } catch (ClassCastException e4) {
            ClassCastException classCastException3 = e4;
            Throwable th12 = th2;
            new WrongType(classCastException3, "arg0", -2, obj2);
            throw th12;
        } catch (ClassCastException e5) {
            ClassCastException classCastException4 = e5;
            Throwable th13 = th3;
            new WrongType(classCastException4, "arg0", -2, obj3);
            throw th13;
        } catch (ClassCastException e6) {
            ClassCastException classCastException5 = e6;
            Throwable th14 = th5;
            new WrongType(classCastException5, "add-to-global-var-environment", 0, obj5);
            throw th14;
        } catch (ClassCastException e7) {
            ClassCastException classCastException6 = e7;
            Throwable th15 = th4;
            new WrongType(classCastException6, "arg0", -2, obj4);
            throw th15;
        } catch (ClassCastException e8) {
            ClassCastException classCastException7 = e8;
            Throwable th16 = th8;
            new WrongType(classCastException7, "add-to-form-environment", 0, obj8);
            throw th16;
        } catch (ClassCastException e9) {
            ClassCastException classCastException8 = e9;
            Throwable th17 = th7;
            new WrongType(classCastException8, "lookup-in-form-environment", 0, obj7);
            throw th17;
        } catch (ClassCastException e10) {
            ClassCastException classCastException9 = e10;
            Throwable th18 = th6;
            new WrongType(classCastException9, "arg0", -2, obj6);
            throw th18;
        } catch (YailRuntimeError e11) {
            processException(e11);
        }
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        Throwable th;
        Throwable th2;
        Throwable th3;
        LList symbols = LList.makeList(argsArray, 0);
        LList lList = symbols;
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object obj = symbols;
        Object obj2 = LList.Empty;
        while (true) {
            Object obj3 = obj2;
            Object obj4 = obj;
            if (obj4 == LList.Empty) {
                Object apply2 = apply.apply2(moduleMethod, LList.reverseInPlace(obj3));
                Object obj5 = apply2;
                try {
                    return misc.string$To$Symbol((CharSequence) apply2);
                } catch (ClassCastException e) {
                    ClassCastException classCastException = e;
                    Throwable th4 = th;
                    new WrongType(classCastException, "string->symbol", 1, obj5);
                    throw th4;
                }
            } else {
                Object obj6 = obj4;
                Object obj7 = obj6;
                try {
                    Pair arg0 = (Pair) obj6;
                    obj = arg0.getCdr();
                    Object car = arg0.getCar();
                    Object obj8 = car;
                    try {
                        obj2 = Pair.make(misc.symbol$To$String((Symbol) car), obj3);
                    } catch (ClassCastException e2) {
                        ClassCastException classCastException2 = e2;
                        Throwable th5 = th3;
                        new WrongType(classCastException2, "symbol->string", 1, obj8);
                        throw th5;
                    }
                } catch (ClassCastException e3) {
                    ClassCastException classCastException3 = e3;
                    Throwable th6 = th2;
                    new WrongType(classCastException3, "arg0", -2, obj7);
                    throw th6;
                }
            }
        }
    }

    static Object lambda2() {
        return null;
    }
}
