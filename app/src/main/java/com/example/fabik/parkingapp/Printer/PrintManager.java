package com.example.fabik.parkingapp.Printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import com.example.fabik.parkingapp.Modelos.Facturados;
import com.example.fabik.parkingapp.Global;
import com.example.fabik.parkingapp.Printer.utils.LogUtil;
//import com.fabianardila.wpossappsmartposterminal.Comun.Global;
//import com.fabianardila.wpossappsmartposterminal.Comun.Utils;
//import com.fabianardila.wpossappsmartposterminal.Printer.utils.LogUtil;
import com.pos.device.config.DevConfig;
import com.pos.device.printer.PrintCanvas;
import com.pos.device.printer.PrintTask;
import com.pos.device.printer.Printer;
import com.pos.device.printer.PrinterCallback;
import com.socsi.smartposapi.printer.Align;
import com.socsi.smartposapi.printer.FontLattice;
import com.socsi.smartposapi.printer.PrintRespCode;
import com.socsi.smartposapi.printer.Printer2;
import com.socsi.smartposapi.printer.TextEntity;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vincent
 * @since 2018.1.9
 * @description:
 *      print manager class
 */
public class PrintManager {;
    private static PrintManager mInstance;

    private static WeakReference<Context> mContextRef;

    private PrintManager() {
    }

    public static void init(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    private static Context getContext() {
        if (mContextRef == null) {
            throw new IllegalStateException("You must init PrinterManager first.");
        }
        return mContextRef.get();
    }

    public static PrintManager getInstance() {
        if (null == mInstance) {
            mInstance = new PrintManager();
        }
        return mInstance;
    }

    private Printer printer = null;
    private PrintTask printTask = null;
    private viewInterface listener;

    /*********************************************************************************************
     Function:	start
     Description: Impresión inicial de pruebas
     **********************************************************************************************/
    public int start(viewInterface listener) {
        this.printTask = new PrintTask();
        int ret = 0;
        this.listener = listener;
            printer = Printer.getInstance();
            if (printer == null) {
                ret = -1;
            } else {
                ret = checkPrinterStatus();
                if (ret != Printer.PRINTER_OK)
                    return ret;
                PrintCanvas canvas = new PrintCanvas();
                Paint paint = new Paint();
                LogUtil.d("print manage begin set text");
                printLine(paint, canvas);
                setFontStyle(paint,2, false);
                canvas.drawText("NEW9220 Android V5.1.1", paint);
                String  sn = DevConfig.getSN();
                canvas.drawText("S/N: "+sn, paint);
                String machineType = DevConfig.getMachine();
                String hardVer = DevConfig.getHardwareVersion();
                canvas.drawText("hardware Version: "+hardVer, paint);
                canvas.drawText("machine :"+machineType,paint);
                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd   HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String   str   =   formatter.format(curDate);

                canvas.drawText("date: "+str,paint);
                printLine(paint, canvas);
                canvas.setX(100);

                setFontStyle(paint,3, false);
                canvas.setX(0);
                canvas.drawText("            HOLA KAREN :)        ",paint);
                printLine(paint, canvas);
                LogUtil.d("begin print tick");
                ret = printData(canvas);
                if (ret == com.pos.device.printer.Printer.PRINTER_OK) {
                    return 0;
                } else {
                    return  -1;
                }
            }
        return ret;
    }

    public int ImpresionTiqueteIngreso(viewInterface listener) {
        int ret = 0;
        if (Build.MODEL.equals("i80")) {
            Printer2 print = Printer2.getInstance();
            print.appendTextEntity2(new TextEntity("PARQUEADERO", null, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity("Sistema de recargas", FontLattice.THIRTY_TWO, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity("Comercio de Prueba", null, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity("Calle General 1254 - Quito", null, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity("0981252525", null, true, Align.CENTER));

            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));

            PrintRespCode printRespCode = print.startPrint();
//
            if (printRespCode != PrintRespCode.Print_Success) {
                if (printRespCode == PrintRespCode.Printer_PaperLack || printRespCode == PrintRespCode.print_Unknow) {
                    return -1;
                }
                else
                    return -1;
            } else {
                return 0;
            }
        } else if (Build.MODEL.equals("NEW9220")) {
            String printerLine;
            this.printTask = new PrintTask();
            this.listener = listener;
            printer = Printer.getInstance();
            if (printer == null) {
                ret = -1;
            } else {
                ret = checkPrinterStatus();
                if (ret != Printer.PRINTER_OK)
                    return ret;
                PrintCanvas canvas = new PrintCanvas();
                Paint paint = new Paint();
                LogUtil.d("print manage begin set text");
                //Inicio de Impresion
                printLine(paint, canvas);
                setFontStyle(paint, 3, true);
                canvas.setX(0);
                canvas.drawText("          PARQUEADERO        ", paint);
                printLine(paint, canvas);

                setFontStyle(paint, 2, true);
                canvas.setX(0);
                canvas.drawText("Tiquete de ingreso", paint);
                setFontStyle(paint, 2, false);
                canvas.setX(0);
                canvas.drawText(" ", paint);
                setFontStyle(paint, 3, false);
                canvas.setX(0);
                printerLine = "Vehiculo de placa " + Global.Placa;
                canvas.drawText(printerLine, paint);
                setFontStyle(paint, 2, false);
                canvas.setX(0);
                printerLine = "Fecha de ingreso " + Global.FechaIngreso;
                canvas.drawText(printerLine, paint);

                printLine(paint, canvas);
                setFontStyle(paint, 3, false);
                canvas.setX(0);
                canvas.drawText("      Horario de Atencion     ", paint);
                setFontStyle(paint, 3, false);
                canvas.setX(0);
                canvas.drawText("          6am - 8pm           ", paint);
                printLine(paint, canvas);
                //Fin de impresion
                LogUtil.d("begin print tick");
                ret = printData(canvas);
                if (ret == com.pos.device.printer.Printer.PRINTER_OK) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }
        return ret;
    }

    public int ImpresionTiqueteSalida(viewInterface listener, Facturados facturados) {
        int ret = 0;
        if (Build.MODEL.equals("i80")) {
            Printer2 print = Printer2.getInstance();
            print.appendTextEntity2(new TextEntity("PARQUEADERO", null, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity("Sistema de recargas", FontLattice.THIRTY_TWO, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity("Comercio de Prueba", null, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity("Calle General 1254 - Quito", null, true, Align.CENTER));
            print.appendTextEntity2(new TextEntity("0981252525", null, true, Align.CENTER));

            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));
            print.appendTextEntity2(new TextEntity(" ", null, false, null));

            PrintRespCode printRespCode = print.startPrint();
//
            if (printRespCode != PrintRespCode.Print_Success) {
                if (printRespCode == PrintRespCode.Printer_PaperLack || printRespCode == PrintRespCode.print_Unknow) {
                    return -1;
                }
                else
                    return -1;
            } else {
                return 0;
            }
        } else if (Build.MODEL.equals("NEW9220")) {
            String printerLine;
            this.printTask = new PrintTask();
            this.listener = listener;
            printer = Printer.getInstance();
            if (printer == null) {
                ret = -1;
            } else {
                ret = checkPrinterStatus();
                if (ret != Printer.PRINTER_OK)
                    return ret;
                PrintCanvas canvas = new PrintCanvas();
                Paint paint = new Paint();
                LogUtil.d("print manage begin set text");
                //Inicio de Impresion
                printLine(paint, canvas);
                setFontStyle(paint, 3, true);
                canvas.setX(0);
                canvas.drawText("          PARQUEADERO        ", paint);
                printLine(paint, canvas);

                setFontStyle(paint, 2, true);
                canvas.setX(0);
                canvas.drawText("Tiquete de ingreso", paint);
                setFontStyle(paint, 2, false);
                canvas.setX(0);
                canvas.drawText(" ", paint);
                setFontStyle(paint, 2, false);
                canvas.setX(0);
                printerLine = "Vehiculo de placa " + facturados.getPlaca();
                canvas.drawText(printerLine, paint);
                setFontStyle(paint, 2, false);
                canvas.setX(0);
                printerLine = "Fecha de salida " + facturados.getFecha_sal();
                canvas.drawText(printerLine, paint);

                setFontStyle(paint, 3, false);
                canvas.setX(0);
                printerLine = "Valor a pagar " + facturados.getValor();
                canvas.drawText(printerLine, paint);

                setFontStyle(paint, 3, false);
                canvas.setX(0);
                printerLine = "Tiempo: " + facturados.getMinutos();
                canvas.drawText(printerLine, paint);

                printLine(paint, canvas);
                setFontStyle(paint, 3, false);
                canvas.setX(0);
                canvas.drawText("      Horario de Atencion     ", paint);
                setFontStyle(paint, 3, false);
                canvas.setX(0);
                canvas.drawText("          6am - 8pm           ", paint);
                printLine(paint, canvas);
                //Fin de impresion
                LogUtil.d("begin print tick");
                ret = printData(canvas);
                if (ret == com.pos.device.printer.Printer.PRINTER_OK) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }
        return ret;
    }

    public static void drawText(String text, Paint paint) {
        int ret;
    }
    /*public static void drawText(String text, Paint paint) {
        //boolean ret;
        String str = Utils.uninterpret_ASCII(array, 0, array.length);

        lpt_puts(str, spaces);
    }*/

    public static String fa;
    public static int xd;

    public static void drawCenter(String cadena) {

        int i;
        int x;

        i = cadena.length();
        x = (42 - (i))/2;

        xd = x * 9;

        String posx = String.valueOf(x);

        System.out.println("Tiene "+ i+" caracteres y se ubicaria en "+posx);
        System.out.println(cadena);
        System.out.println("Valor X es "+String.valueOf(xd));

        fa =cadena;

    }

    public int prueba(viewInterface listener){


        String printerLine;
        this.printTask = new PrintTask();
        int ret;
        this.listener = listener;
        printer = Printer.getInstance();
        if (printer == null) {
            ret = -1;
        } else {
            ret = checkPrinterStatus();
            if (ret != Printer.PRINTER_OK)
                return ret;

            PrintCanvas canvas = new PrintCanvas();
            Paint paint = new Paint();

            Printer printer = new Printer();


            LogUtil.d("print manage begin set text");
            //printLine(paint, canvas);
            printLine(paint, canvas);

            int ancho = printer.getWidth();
            int alto = canvas.getDensity();
            int temp = printer.getTemperature();


            System.out.println("Este es el ancho"+ancho);
            System.out.println("Este es el alto"+alto);
            System.out.println("Temperatura "+temp);

            printerLine = "COLILLA: 35E   0002175";
            drawCenter(printerLine);
            canvas.setX(xd);
            canvas.drawText(fa, paint);
            printerLine = "Una prueba";
            drawCenter(printerLine);
            canvas.setX(xd);
            canvas.drawText(fa, paint);
            printerLine= "Otra gran prueba xD";
            drawCenter(printerLine);
            canvas.setX(xd);
            canvas.drawText(fa, paint);


            printerLine= "|";
            canvas.setX(380);
            canvas.drawText(printerLine, paint);

            printerLine="012345678901234567890123456789012345678901";
            canvas.drawText(printerLine, paint);

            printerLine="012345678901234567890123456789012345678901";
            setFontStyle(paint,1, true);
            canvas.drawText(printerLine, paint);


            printerLine="01234567890123456789012345678";
            setFontStyle(paint,2, true);
            canvas.drawText(printerLine, paint);

            printerLine="01234567890123456789012345678";
            setFontStyle(paint,2, false);
            canvas.drawText(printerLine, paint);

            int status = printer.getStatus();
            System.out.println("Status "+status);

            canvas.drawLine(70, 0, 70, 50, paint);
            canvas.drawLine(73, 0, 73, 50, paint);
            canvas.drawLine(0, 100, 50, 100, paint);
            paint.setTextSize(30);
            paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            canvas.drawText("FACTURA DE VENTA",paint);
            Bitmap prueba = canvas.getBitmap();
            System.out.println("Tamano bitmap "+prueba.getWidth());



            canvas.drawText("NOMBRE DEL CLIENTE: ", 85, 280, paint);
            canvas.drawText("NUMERO DE DOCUMENTO: ", 85, 360, paint);
            canvas.drawText("PRODUCTO COMPRADO: ", 85, 440, paint);
            canvas.drawText("VALOR DE LA FACTURA: ", 85, 520, paint);

            LogUtil.d("begin print tick");
            ret = printData(canvas);
            if (ret == com.pos.device.printer.Printer.PRINTER_OK) {
                return 0;
            } else {
                return  -1;
            }

        }
        return ret;
    }

    public int impresionNuevoTiquete(viewInterface listener){

        String printerLine;
        this.printTask = new PrintTask();
        int ret;
        this.listener = listener;
        printer = Printer.getInstance();
        if (printer == null) {
            ret = -1;
        } else {
            ret = checkPrinterStatus();
            if (ret != Printer.PRINTER_OK)
                return ret;
            PrintCanvas canvas = new PrintCanvas();
            Paint paint = new Paint();



            LogUtil.d("print manage begin set text");
            //printLine(paint, canvas);



            printLine(paint, canvas);
            setFontStyle(paint,2, false);
            canvas.setX(375);



            printerLine = "||";
            canvas.drawText(printerLine + "\n", paint);

            int xPos = (375) / 2;
            int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;
            canvas.setX(xPos);
            canvas.setY(yPos);
            printerLine = "COLILLA:" + "  35E   0002175";
            canvas.drawText(printerLine + "\n", paint);



            printerLine = "LA PERLA";
            //lpt_puts_center(printerLine, Global.lengthLine);
            printerLine = "4 CIFRAS 5000 VECES X $1";
            //lpt_puts_center(printerLine, Global.lengthLine);
            printerLine = "4 CIFRAS 5000 VECES X $1";
            //lpt_puts_center(printerLine, Global.lengthLine);
            canvas.drawText("\n", paint);
            printerLine = ("F. Venta: ");
            canvas.drawText(printerLine,paint);
            printerLine = ("F. Sorteo: ");
            canvas.drawText(printerLine, paint);
            printerLine = "     ";
            canvas.drawText(printerLine, paint);
            printerLine = ("Vendedor: ");
            canvas.drawText(printerLine, paint);
            printerLine = "LOT: SANTANDER";
            canvas.drawText(printerLine, paint);
            canvas.drawText("\n", paint);
    /*
            //lpt_format(Global.SIZE_NORMAL, 1, 0, 0, 0, 0, 0);
            String Datos = String.format("%1$-4s| %2$5s|%3$5s|%4$5s|%5$5s", "Num", "Pleno", "Comb", "Pata", "Una");
            //lpt_puts_center(Datos, Global.lengthLine);
            //lpt_puts("No      Dir    Pat    Com    Una", true);
            //lpt_puts("\n", false);



            for (int i = 0; i < colum1.length; i++) {
                String colu1 = colum1[i];
                String colu2 = colum2[i];
                String colu3 = colum3[i];
                String colu4 = colum4[i];
                String colu5 = colum5[i];

                // Add padding to the right.
                // ... Add padding to the left.
                String value = String.format("%1$-4s| %2$5s|%3$5s|%4$5s|%5$5s", colu1, colu2, colu3, colu4, colu5);
                canvas.setX(100);
                canvas.drawText(value, paint);
            }
            */

            LogUtil.d("begin print tick");
            ret = printData(canvas);
            if (ret == com.pos.device.printer.Printer.PRINTER_OK) {
                return 0;
            } else {
                return  -1;
            }

        }

            return ret;

        }



        /*********************************************************************************************
         Function:	imprimirTiquete
         Description: Imprime el tiquete
        **********************************************************************************************/
    public int imprimirTiquete(viewInterface listener) {
        String printerLine;
        this.printTask = new PrintTask();
        int ret;
        this.listener = listener;
        printer = Printer.getInstance();
        if (printer == null) {
            ret = -1;
        } else {
            ret = checkPrinterStatus();
            if (ret != Printer.PRINTER_OK)
                return ret;
            PrintCanvas canvas = new PrintCanvas();
            Paint paint = new Paint();
            LogUtil.d("print manage begin set text");
            //printLine(paint, canvas);



            printerLine = "TIQUETE: ";
            canvas.drawText(printerLine + "\n", paint);

            printerLine = "COLILLA:" + "  35E   0002175";
            canvas.drawText(printerLine + "\n", paint);

            printerLine = "CHEQUEO:" + "AS0000152BCD1212";
            canvas.drawText(printerLine + "\n", paint);

            printerLine = "ASESORA:" + "123456" + " M:" + "2383637";
            canvas.drawText(printerLine + "\n", paint);

            printerLine = "FECHA SORTEO:";
            canvas.drawText(printerLine + "\n", paint);

            printerLine = "BOGOTA";
            canvas.drawText(printerLine + "\n\n", paint);

            printerLine = "No             Dir          Pat          Com         Una";
            canvas.drawText(printerLine + "\n", paint);
            printerLine = "2463    1000             0         2000              0";
            canvas.drawText(printerLine + "\n", paint);
            printerLine = "*365       500      3000          300       1000";
            canvas.drawText(printerLine + "\n", paint);
            printerLine = "*246    2000         300          200              0";
            canvas.drawText(printerLine + "\n", paint);
            printerLine = "****\n****\n****\n";
            canvas.drawText(printerLine, paint);

            setFontStyle(paint,2, true);

            printerLine = "Apos:$ " +  " Iv(" + "19" + "):$ " ;
            canvas.drawText(printerLine + "\n", paint);

            printerLine = "Paga:$ " + " Encima:$ ";
            canvas.drawText(printerLine + "\n", paint);

            setFontStyle(paint,1, true);

            printerLine = "Contrato concesion 68 de 2016-LOT. BOGOTA TEL - PBX: 3351535";
            canvas.drawText(printerLine + "\n", paint);

            /*
            printLine(paint, canvas);
            canvas.setX(100);
            Bitmap image = BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.lotbogota);
            canvas.drawBitmap(image, paint);
            if (!image.isRecycled()) {
                image.recycle();
            }
            setFontStyle(paint,3, false);
            canvas.setX(0);
            */

            LogUtil.d("begin print tick");
            ret = printData(canvas);
            if (ret == com.pos.device.printer.Printer.PRINTER_OK) {
                return 0;
            } else {
                return  -1;
            }
        }
        return ret;
    }


    /*********************************************************************************************
     Function: saltoPapel
     Description: Imprime un salto de papel
     **********************************************************************************************/
    public int saltoPapel(viewInterface listener) {
        String printerLine;
        this.printTask = new PrintTask();
        int ret = 0;
        this.listener = listener;
        printer = Printer.getInstance();
        if (printer == null) {
            ret = -1;
        } else {
            ret = checkPrinterStatus();
            if (ret != Printer.PRINTER_OK)
                return ret;
            PrintCanvas canvas = new PrintCanvas();
            Paint paint = new Paint();

            setFontStyle(paint,1, true);
            printerLine = "\n";
            canvas.drawText(printerLine, paint);

            ret = printData(canvas);
            if (ret == Printer.PRINTER_OK) {
                return 0;
            } else {
                return  -1;
            }
        }
        return ret;
    }

    private void setFontStyle(Paint paint, int size, boolean isBold) {
        if (isBold)
            paint.setTypeface(Typeface.DEFAULT_BOLD);
        else
            paint.setTypeface(Typeface.DEFAULT);
        switch (size) {
            case 0:
                break;
            case 1:
                paint.setTextSize(16F);
                break;
            case 2:
                paint.setTextSize(22F);
                break;
            case 3:
                paint.setTextSize(30F);
                break;
            default:
                break;
        }
    }

    /**
     * print data
     * @param pCanvas
     * @return
     */
    private int printData(PrintCanvas pCanvas) {
        printTask.setPrintCanvas(pCanvas);
        int ret = printer.getStatus();
        if (ret != Printer.PRINTER_OK)
            return ret;
        printer.startPrint(printTask, new PrinterCallback() {
            @Override
            public void onResult(int ret, PrintTask printTask) {
                if (ret == Printer.PRINTER_OK) {
                    //listener.showMsg("printer test result: true", Presenter.MSG_RESULT);
                } else if (ret == Printer.PRINTER_STATUS_PAPER_LACK) {
                    //listener.showMsg("printer test result: false(lack of paper)", Presenter.MSG_RESULT);
                } else if (ret == Printer.PRINTER_STATUS_BUSY) {
                    //listener.showMsg("printer test result: true(printer busy)", Presenter.MSG_RESULT);
                } else {
                    //listener.showMsg("printer test result: true(status: "+ret, Integer.parseInt(Presenter.MSG_RESULT+")"));
                }
            }
        });
        return ret;
    }

    /**
     * check status of print device
     * @return
     */
    private int checkPrinterStatus() {
        long t0 = System.currentTimeMillis();
        int ret;
        while (true) {
            if (System.currentTimeMillis() - t0 > 30000) {
                ret = -1;
                break;
            }
            ret = printer.getStatus();
            if (ret == Printer.PRINTER_OK) {
                LogUtil.e("print device work");
                break;
            } else if (ret == -3) {
                LogUtil.e("print device lack of paper...");
                break;
            } else if (ret == Printer.PRINTER_STATUS_BUSY) {
                LogUtil.e("print device busy");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
        return ret;
    }


    /**
     * paint a line
     * @param paint
     * @param canvas
     */
    private void printLine(Paint paint, PrintCanvas canvas) {
        String line = "----------------------------------------------------------------";
        setFontStyle(paint, 1, true);
        canvas.drawText(line, paint);
    }

}
