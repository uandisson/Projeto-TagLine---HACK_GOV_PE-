package p004io.kodular.tagline.Tagline;

import android.support.p000v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Map;
import com.google.appinventor.components.runtime.Marker;
import com.google.appinventor.components.runtime.Notifier;
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
import org.jose4j.jws.AlgorithmIdentifiers;

/* renamed from: io.kodular.tagline.Tagline.Screen3 */
/* compiled from: Screen3.yail */
public class Screen3 extends Form implements Runnable {
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final FString Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final IntNum Lit17 = IntNum.make(-2);
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final FString Lit23;
    static final FString Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final DFloNum Lit28 = DFloNum.make(-8.064328574839289d);
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final DFloNum Lit30 = DFloNum.make(-34.90493774414063d);
    static final FString Lit31;
    static final FString Lit32;
    static final SimpleSymbol Lit33;
    static final DFloNum Lit34 = DFloNum.make(-8.045972062733982d);
    static final DFloNum Lit35 = DFloNum.make(-34.88948822021485d);
    static final FString Lit36;
    static final FString Lit37;
    static final SimpleSymbol Lit38;
    static final DFloNum Lit39 = DFloNum.make(-8.080304860930505d);
    static final IntNum Lit4;
    static final DFloNum Lit40 = DFloNum.make(-34.90699768066407d);
    static final FString Lit41;
    static final FString Lit42;
    static final SimpleSymbol Lit43;
    static final DFloNum Lit44 = DFloNum.make(-8.064052383488752d);
    static final DFloNum Lit45 = DFloNum.make(-34.889123439788825d);
    static final FString Lit46;
    static final FString Lit47;
    static final SimpleSymbol Lit48;
    static final DFloNum Lit49 = DFloNum.make(-8.0651359023208d);
    static final SimpleSymbol Lit5;
    static final DFloNum Lit50 = DFloNum.make(-34.885132312774665d);
    static final FString Lit51;
    static final FString Lit52;
    static final SimpleSymbol Lit53;
    static final DFloNum Lit54 = DFloNum.make(-8.057020063266835d);
    static final DFloNum Lit55 = DFloNum.make(-34.89618301391602d);
    static final FString Lit56;
    static final FString Lit57;
    static final SimpleSymbol Lit58;
    static final DFloNum Lit59 = DFloNum.make(-8.071127071674166d);
    static final SimpleSymbol Lit6;
    static final DFloNum Lit60 = DFloNum.make(-34.89601135253907d);
    static final FString Lit61;
    static final SimpleSymbol Lit62;
    static final SimpleSymbol Lit63;
    static final PairWithPosition Lit64;
    static final SimpleSymbol Lit65;
    static final SimpleSymbol Lit66;
    static final FString Lit67;
    static final FString Lit68;
    static final SimpleSymbol Lit69;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70;
    static final SimpleSymbol Lit71;
    static final SimpleSymbol Lit72;
    static final SimpleSymbol Lit73;
    static final SimpleSymbol Lit74;
    static final SimpleSymbol Lit75;
    static final SimpleSymbol Lit76;
    static final SimpleSymbol Lit77;
    static final SimpleSymbol Lit78;
    static final SimpleSymbol Lit79;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit80;
    static final SimpleSymbol Lit81;
    static final SimpleSymbol Lit82;
    static final SimpleSymbol Lit9;
    public static Screen3 Screen3;
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
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
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
        FString fString;
        FString fString2;
        SimpleSymbol simpleSymbol15;
        SimpleSymbol simpleSymbol16;
        SimpleSymbol simpleSymbol17;
        SimpleSymbol simpleSymbol18;
        SimpleSymbol simpleSymbol19;
        FString fString3;
        SimpleSymbol simpleSymbol20;
        FString fString4;
        FString fString5;
        SimpleSymbol simpleSymbol21;
        FString fString6;
        FString fString7;
        SimpleSymbol simpleSymbol22;
        FString fString8;
        FString fString9;
        SimpleSymbol simpleSymbol23;
        FString fString10;
        FString fString11;
        SimpleSymbol simpleSymbol24;
        FString fString12;
        FString fString13;
        SimpleSymbol simpleSymbol25;
        FString fString14;
        FString fString15;
        SimpleSymbol simpleSymbol26;
        SimpleSymbol simpleSymbol27;
        SimpleSymbol simpleSymbol28;
        SimpleSymbol simpleSymbol29;
        FString fString16;
        FString fString17;
        SimpleSymbol simpleSymbol30;
        SimpleSymbol simpleSymbol31;
        SimpleSymbol simpleSymbol32;
        SimpleSymbol simpleSymbol33;
        SimpleSymbol simpleSymbol34;
        SimpleSymbol simpleSymbol35;
        SimpleSymbol simpleSymbol36;
        SimpleSymbol simpleSymbol37;
        FString fString18;
        SimpleSymbol simpleSymbol38;
        SimpleSymbol simpleSymbol39;
        SimpleSymbol simpleSymbol40;
        SimpleSymbol simpleSymbol41;
        SimpleSymbol simpleSymbol42;
        SimpleSymbol simpleSymbol43;
        SimpleSymbol simpleSymbol44;
        SimpleSymbol simpleSymbol45;
        SimpleSymbol simpleSymbol46;
        SimpleSymbol simpleSymbol47;
        SimpleSymbol simpleSymbol48;
        new SimpleSymbol("lookup-handler");
        Lit82 = (SimpleSymbol) simpleSymbol.readResolve();
        new SimpleSymbol("dispatchGenericEvent");
        Lit81 = (SimpleSymbol) simpleSymbol2.readResolve();
        new SimpleSymbol("dispatchEvent");
        Lit80 = (SimpleSymbol) simpleSymbol3.readResolve();
        new SimpleSymbol("send-error");
        Lit79 = (SimpleSymbol) simpleSymbol4.readResolve();
        new SimpleSymbol("add-to-form-do-after-creation");
        Lit78 = (SimpleSymbol) simpleSymbol5.readResolve();
        new SimpleSymbol("add-to-global-vars");
        Lit77 = (SimpleSymbol) simpleSymbol6.readResolve();
        new SimpleSymbol("add-to-components");
        Lit76 = (SimpleSymbol) simpleSymbol7.readResolve();
        new SimpleSymbol("add-to-events");
        Lit75 = (SimpleSymbol) simpleSymbol8.readResolve();
        new SimpleSymbol("add-to-global-var-environment");
        Lit74 = (SimpleSymbol) simpleSymbol9.readResolve();
        new SimpleSymbol("is-bound-in-form-environment");
        Lit73 = (SimpleSymbol) simpleSymbol10.readResolve();
        new SimpleSymbol("lookup-in-form-environment");
        Lit72 = (SimpleSymbol) simpleSymbol11.readResolve();
        new SimpleSymbol("add-to-form-environment");
        Lit71 = (SimpleSymbol) simpleSymbol12.readResolve();
        new SimpleSymbol("android-log-form");
        Lit70 = (SimpleSymbol) simpleSymbol13.readResolve();
        new SimpleSymbol("get-simple-name");
        Lit69 = (SimpleSymbol) simpleSymbol14.readResolve();
        new FString("com.google.appinventor.components.runtime.Notifier");
        Lit68 = fString;
        new FString("com.google.appinventor.components.runtime.Notifier");
        Lit67 = fString2;
        new SimpleSymbol("Click");
        Lit66 = (SimpleSymbol) simpleSymbol15.readResolve();
        new SimpleSymbol("Marker7$Click");
        Lit65 = (SimpleSymbol) simpleSymbol16.readResolve();
        new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT);
        SimpleSymbol simpleSymbol49 = (SimpleSymbol) simpleSymbol17.readResolve();
        Lit7 = simpleSymbol49;
        Lit64 = PairWithPosition.make(simpleSymbol49, PairWithPosition.make(Lit7, PairWithPosition.make(Lit7, LList.Empty, "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen3.yail", 467083), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen3.yail", 467078), "/tmp/1613066542848_0.683030498808931-0/youngandroidproject/../src/io/kodular/tagline/Tagline/Screen3.yail", 467072);
        new SimpleSymbol("ShowMessageDialog");
        Lit63 = (SimpleSymbol) simpleSymbol18.readResolve();
        new SimpleSymbol("Notifier1");
        Lit62 = (SimpleSymbol) simpleSymbol19.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit61 = fString3;
        new SimpleSymbol("Marker7");
        Lit58 = (SimpleSymbol) simpleSymbol20.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit57 = fString4;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit56 = fString5;
        new SimpleSymbol("Marker6");
        Lit53 = (SimpleSymbol) simpleSymbol21.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit52 = fString6;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit51 = fString7;
        new SimpleSymbol("Marker5");
        Lit48 = (SimpleSymbol) simpleSymbol22.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit47 = fString8;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit46 = fString9;
        new SimpleSymbol("Marker4");
        Lit43 = (SimpleSymbol) simpleSymbol23.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit42 = fString10;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit41 = fString11;
        new SimpleSymbol("Marker3");
        Lit38 = (SimpleSymbol) simpleSymbol24.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit37 = fString12;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit36 = fString13;
        new SimpleSymbol("Marker2");
        Lit33 = (SimpleSymbol) simpleSymbol25.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit32 = fString14;
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit31 = fString15;
        new SimpleSymbol("Longitude");
        Lit29 = (SimpleSymbol) simpleSymbol26.readResolve();
        new SimpleSymbol("Latitude");
        Lit27 = (SimpleSymbol) simpleSymbol27.readResolve();
        new SimpleSymbol("ImageAsset");
        Lit26 = (SimpleSymbol) simpleSymbol28.readResolve();
        new SimpleSymbol("Marker1");
        Lit25 = (SimpleSymbol) simpleSymbol29.readResolve();
        new FString("com.google.appinventor.components.runtime.Marker");
        Lit24 = fString16;
        new FString("com.google.appinventor.components.runtime.Map");
        Lit23 = fString17;
        new SimpleSymbol("ShowZoom");
        Lit22 = (SimpleSymbol) simpleSymbol30.readResolve();
        new SimpleSymbol("ShowUser");
        Lit21 = (SimpleSymbol) simpleSymbol31.readResolve();
        new SimpleSymbol("boolean");
        Lit20 = (SimpleSymbol) simpleSymbol32.readResolve();
        new SimpleSymbol("ShowScale");
        Lit19 = (SimpleSymbol) simpleSymbol33.readResolve();
        new SimpleSymbol("Width");
        Lit18 = (SimpleSymbol) simpleSymbol34.readResolve();
        new SimpleSymbol("Height");
        Lit16 = (SimpleSymbol) simpleSymbol35.readResolve();
        new SimpleSymbol("CenterFromString");
        Lit15 = (SimpleSymbol) simpleSymbol36.readResolve();
        new SimpleSymbol("Map1");
        Lit14 = (SimpleSymbol) simpleSymbol37.readResolve();
        new FString("com.google.appinventor.components.runtime.Map");
        Lit13 = fString18;
        new SimpleSymbol("Title");
        Lit12 = (SimpleSymbol) simpleSymbol38.readResolve();
        new SimpleSymbol("Theme");
        Lit11 = (SimpleSymbol) simpleSymbol39.readResolve();
        new SimpleSymbol("ReceiveSharedText");
        Lit10 = (SimpleSymbol) simpleSymbol40.readResolve();
        new SimpleSymbol("PackageName");
        Lit9 = (SimpleSymbol) simpleSymbol41.readResolve();
        new SimpleSymbol("AppName");
        Lit8 = (SimpleSymbol) simpleSymbol42.readResolve();
        new SimpleSymbol("AppId");
        Lit6 = (SimpleSymbol) simpleSymbol43.readResolve();
        new SimpleSymbol("number");
        Lit5 = (SimpleSymbol) simpleSymbol44.readResolve();
        int[] iArr = new int[2];
        iArr[0] = -1;
        Lit4 = IntNum.make(iArr);
        new SimpleSymbol("AccentColor");
        Lit3 = (SimpleSymbol) simpleSymbol45.readResolve();
        new SimpleSymbol("*the-null-value*");
        Lit2 = (SimpleSymbol) simpleSymbol46.readResolve();
        new SimpleSymbol("getMessage");
        Lit1 = (SimpleSymbol) simpleSymbol47.readResolve();
        new SimpleSymbol("Screen3");
        Lit0 = (SimpleSymbol) simpleSymbol48.readResolve();
    }

    public Screen3() {
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
        ModuleInfo.register(this);
        ModuleMethod moduleMethod36 = moduleMethod;
        new frame();
        frame frame3 = frame2;
        frame3.$main = this;
        frame frame4 = frame3;
        new ModuleMethod(frame4, 1, Lit69, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.get$Mnsimple$Mnname = moduleMethod36;
        new ModuleMethod(frame4, 2, Lit70, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = moduleMethod2;
        new ModuleMethod(frame4, 3, Lit71, 8194);
        this.add$Mnto$Mnform$Mnenvironment = moduleMethod3;
        new ModuleMethod(frame4, 4, Lit72, 8193);
        this.lookup$Mnin$Mnform$Mnenvironment = moduleMethod4;
        new ModuleMethod(frame4, 6, Lit73, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = moduleMethod5;
        new ModuleMethod(frame4, 7, Lit74, 8194);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = moduleMethod6;
        new ModuleMethod(frame4, 8, Lit75, 8194);
        this.add$Mnto$Mnevents = moduleMethod7;
        new ModuleMethod(frame4, 9, Lit76, 16388);
        this.add$Mnto$Mncomponents = moduleMethod8;
        new ModuleMethod(frame4, 10, Lit77, 8194);
        this.add$Mnto$Mnglobal$Mnvars = moduleMethod9;
        new ModuleMethod(frame4, 11, Lit78, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = moduleMethod10;
        new ModuleMethod(frame4, 12, Lit79, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = moduleMethod11;
        new ModuleMethod(frame4, 13, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = moduleMethod12;
        new ModuleMethod(frame4, 14, Lit80, 16388);
        this.dispatchEvent = moduleMethod13;
        new ModuleMethod(frame4, 15, Lit81, 16388);
        this.dispatchGenericEvent = moduleMethod14;
        new ModuleMethod(frame4, 16, Lit82, 8194);
        this.lookup$Mnhandler = moduleMethod15;
        new ModuleMethod(frame4, 17, (Object) null, 0);
        ModuleMethod moduleMethod37 = moduleMethod16;
        moduleMethod37.setProperty("source-location", "/tmp/runtime8397583071109955809.scm:615");
        lambda$Fn1 = moduleMethod37;
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
        new ModuleMethod(frame4, 23, (Object) null, 0);
        lambda$Fn6 = moduleMethod22;
        new ModuleMethod(frame4, 24, (Object) null, 0);
        lambda$Fn7 = moduleMethod23;
        new ModuleMethod(frame4, 25, (Object) null, 0);
        lambda$Fn8 = moduleMethod24;
        new ModuleMethod(frame4, 26, (Object) null, 0);
        lambda$Fn9 = moduleMethod25;
        new ModuleMethod(frame4, 27, (Object) null, 0);
        lambda$Fn10 = moduleMethod26;
        new ModuleMethod(frame4, 28, (Object) null, 0);
        lambda$Fn11 = moduleMethod27;
        new ModuleMethod(frame4, 29, (Object) null, 0);
        lambda$Fn12 = moduleMethod28;
        new ModuleMethod(frame4, 30, (Object) null, 0);
        lambda$Fn13 = moduleMethod29;
        new ModuleMethod(frame4, 31, (Object) null, 0);
        lambda$Fn14 = moduleMethod30;
        new ModuleMethod(frame4, 32, (Object) null, 0);
        lambda$Fn15 = moduleMethod31;
        new ModuleMethod(frame4, 33, (Object) null, 0);
        lambda$Fn16 = moduleMethod32;
        new ModuleMethod(frame4, 34, (Object) null, 0);
        lambda$Fn17 = moduleMethod33;
        new ModuleMethod(frame4, 35, (Object) null, 0);
        lambda$Fn18 = moduleMethod34;
        new ModuleMethod(frame4, 36, Lit65, 0);
        this.Marker7$Click = moduleMethod35;
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
        Screen3 = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        C1234runtime.$instance.run();
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
            Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit6, "5731346200657920", Lit7);
            Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "Tagline", Lit7);
            Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit9, "br.com.tagline", Lit7);
            Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit10, AlgorithmIdentifiers.NONE, Lit7);
            Object andCoerceProperty$Ex6 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit11, "AppTheme.Light.DarkActionBar", Lit7);
            Values.writeValues(C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit12, "Mapa", Lit7), $result);
        } else {
            new Promise(lambda$Fn2);
            addToFormDoAfterCreation(obj2);
        }
        this.Map1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit13, Lit14, lambda$Fn3), $result);
        } else {
            addToComponents(Lit0, Lit23, Lit14, lambda$Fn4);
        }
        this.Marker1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit24, Lit25, lambda$Fn5), $result);
        } else {
            addToComponents(Lit14, Lit31, Lit25, lambda$Fn6);
        }
        this.Marker2 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit32, Lit33, lambda$Fn7), $result);
        } else {
            addToComponents(Lit14, Lit36, Lit33, lambda$Fn8);
        }
        this.Marker3 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit37, Lit38, lambda$Fn9), $result);
        } else {
            addToComponents(Lit14, Lit41, Lit38, lambda$Fn10);
        }
        this.Marker4 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit42, Lit43, lambda$Fn11), $result);
        } else {
            addToComponents(Lit14, Lit46, Lit43, lambda$Fn12);
        }
        this.Marker5 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit47, Lit48, lambda$Fn13), $result);
        } else {
            addToComponents(Lit14, Lit51, Lit48, lambda$Fn14);
        }
        this.Marker6 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit52, Lit53, lambda$Fn15), $result);
        } else {
            addToComponents(Lit14, Lit56, Lit53, lambda$Fn16);
        }
        this.Marker7 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit14, Lit57, Lit58, lambda$Fn17), $result);
        } else {
            addToComponents(Lit14, Lit61, Lit58, lambda$Fn18);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Object addToCurrentFormEnvironment = C1234runtime.addToCurrentFormEnvironment(Lit65, this.Marker7$Click);
        } else {
            addToFormEnvironment(Lit65, this.Marker7$Click);
        }
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) C1234runtime.$Stthis$Mnform$St, "Marker7", "Click");
        } else {
            addToEvents(Lit58, Lit66);
        }
        this.Notifier1 = null;
        if (C1234runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(C1234runtime.addComponentWithinRepl(Lit0, Lit67, Lit62, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit68, Lit62, Boolean.FALSE);
        }
        C1234runtime.initRuntime();
    }

    static Object lambda3() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit6, "5731346200657920", Lit7);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "Tagline", Lit7);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit9, "br.com.tagline", Lit7);
        Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit10, AlgorithmIdentifiers.NONE, Lit7);
        Object andCoerceProperty$Ex6 = C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit11, "AppTheme.Light.DarkActionBar", Lit7);
        return C1234runtime.setAndCoerceProperty$Ex(Lit0, Lit12, "Mapa", Lit7);
    }

    static Object lambda4() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit15, "-8.05428, -34.8813", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit16, Lit17, Lit5);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit18, Lit17, Lit5);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit19, Boolean.TRUE, Lit20);
        Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit21, Boolean.TRUE, Lit20);
        return C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit22, Boolean.TRUE, Lit20);
    }

    static Object lambda5() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit15, "-8.05428, -34.8813", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit16, Lit17, Lit5);
        Object andCoerceProperty$Ex3 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit18, Lit17, Lit5);
        Object andCoerceProperty$Ex4 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit19, Boolean.TRUE, Lit20);
        Object andCoerceProperty$Ex5 = C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit21, Boolean.TRUE, Lit20);
        return C1234runtime.setAndCoerceProperty$Ex(Lit14, Lit22, Boolean.TRUE, Lit20);
    }

    static Object lambda6() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit25, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit25, Lit27, Lit28, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit25, Lit29, Lit30, Lit5);
    }

    static Object lambda7() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit25, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit25, Lit27, Lit28, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit25, Lit29, Lit30, Lit5);
    }

    static Object lambda8() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit33, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit33, Lit27, Lit34, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit33, Lit29, Lit35, Lit5);
    }

    static Object lambda9() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit33, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit33, Lit27, Lit34, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit33, Lit29, Lit35, Lit5);
    }

    static Object lambda10() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit38, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit38, Lit27, Lit39, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit38, Lit29, Lit40, Lit5);
    }

    static Object lambda11() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit38, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit38, Lit27, Lit39, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit38, Lit29, Lit40, Lit5);
    }

    static Object lambda12() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit43, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit43, Lit27, Lit44, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit43, Lit29, Lit45, Lit5);
    }

    static Object lambda13() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit43, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit43, Lit27, Lit44, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit43, Lit29, Lit45, Lit5);
    }

    static Object lambda14() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit48, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit48, Lit27, Lit49, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit48, Lit29, Lit50, Lit5);
    }

    static Object lambda15() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit48, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit48, Lit27, Lit49, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit48, Lit29, Lit50, Lit5);
    }

    static Object lambda16() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit27, Lit54, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit29, Lit55, Lit5);
    }

    static Object lambda17() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit26, "bicicleta-de-estrada_(1).png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit27, Lit54, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit53, Lit29, Lit55, Lit5);
    }

    static Object lambda18() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit58, Lit26, "carro.png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit58, Lit27, Lit59, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit58, Lit29, Lit60, Lit5);
    }

    static Object lambda19() {
        Object andCoerceProperty$Ex = C1234runtime.setAndCoerceProperty$Ex(Lit58, Lit26, "carro.png", Lit7);
        Object andCoerceProperty$Ex2 = C1234runtime.setAndCoerceProperty$Ex(Lit58, Lit27, Lit59, Lit5);
        return C1234runtime.setAndCoerceProperty$Ex(Lit58, Lit29, Lit60, Lit5);
    }

    public Object Marker7$Click() {
        C1234runtime.setThisForm();
        return C1234runtime.callComponentMethod(Lit62, Lit63, LList.list3("PERIGO MUITO PRÓXIMO!!!", "Alerta!", "Ok"), Lit64);
    }

    /* renamed from: io.kodular.tagline.Tagline.Screen3$frame */
    /* compiled from: Screen3.yail */
    public class frame extends ModuleBody {
        Screen3 $main;

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
                    if (!(obj7 instanceof Screen3)) {
                        return -786431;
                    }
                    callContext5.value1 = obj8;
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
                    if (!(obj9 instanceof Screen3)) {
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
                    if (!(obj17 instanceof Screen3)) {
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
                    return Screen3.lambda2();
                case 18:
                    this.$main.$define();
                    return Values.empty;
                case 19:
                    return Screen3.lambda3();
                case 20:
                    return Screen3.lambda4();
                case 21:
                    return Screen3.lambda5();
                case 22:
                    return Screen3.lambda6();
                case 23:
                    return Screen3.lambda7();
                case 24:
                    return Screen3.lambda8();
                case 25:
                    return Screen3.lambda9();
                case 26:
                    return Screen3.lambda10();
                case 27:
                    return Screen3.lambda11();
                case 28:
                    return Screen3.lambda12();
                case 29:
                    return Screen3.lambda13();
                case 30:
                    return Screen3.lambda14();
                case 31:
                    return Screen3.lambda15();
                case 32:
                    return Screen3.lambda16();
                case 33:
                    return Screen3.lambda17();
                case 34:
                    return Screen3.lambda18();
                case 35:
                    return Screen3.lambda19();
                case 36:
                    return this.$main.Marker7$Click();
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
                case 23:
                    callContext2.proc = moduleMethod2;
                    callContext2.f239pc = 0;
                    return 0;
                case 24:
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
        Screen3 = this;
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
