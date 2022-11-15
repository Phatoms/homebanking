package com.santander.homebanking.services;

import com.santander.homebanking.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,
                          String subject,
                          String user,
                          String cardType,
                          String token) throws MessagingException {
        user = StringUtils.capitalize(user);
        cardType = cardType.toLowerCase();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <title> Welcome to [Coded Mails] </title>\n" +
                "  <!--[if !mso]><!-- -->\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <!--<![endif]-->\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <style type=\"text/css\">\n" +
                "    #outlook a {\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -webkit-text-size-adjust: 100%;\n" +
                "      -ms-text-size-adjust: 100%;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "      border-collapse: collapse;\n" +
                "      mso-table-lspace: 0pt;\n" +
                "      mso-table-rspace: 0pt;\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "      border: 0;\n" +
                "      height: auto;\n" +
                "      line-height: 100%;\n" +
                "      outline: none;\n" +
                "      text-decoration: none;\n" +
                "      -ms-interpolation-mode: bicubic;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      display: block;\n" +
                "      margin: 13px 0;\n" +
                "    }\n" +
                "  </style>\n" +
                "  <!--[if mso]>\n" +
                "        <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "          <o:AllowPNG/>\n" +
                "          <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "        </xml>\n" +
                "        <![endif]-->\n" +
                "  <!--[if lte mso 11]>\n" +
                "        <style type=\"text/css\">\n" +
                "          .mj-outlook-group-fix { width:100% !important; }\n" +
                "        </style>\n" +
                "        <![endif]-->\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Nunito:100,400,700\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "  <style type=\"text/css\">\n" +
                "    @import url(https://fonts.googleapis.com/css?family=Nunito:100,400,700);\n" +
                "  </style>\n" +
                "  <!--<![endif]-->\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width:480px) {\n" +
                "      .mj-column-per-100 {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (max-width:480px) {\n" +
                "      table.mj-full-width-mobile {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      td.mj-full-width-mobile {\n" +
                "        width: auto !important;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "  <style type=\"text/css\">\n" +
                "    a,\n" +
                "    span,\n" +
                "    td,\n" +
                "    th {\n" +
                "      -webkit-font-smoothing: antialiased !important;\n" +
                "      -moz-osx-font-smoothing: grayscale !important;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color:#eaeaea;\">\n" +
                "  <div style=\"display:none;font-size:1px;color:#ffffff;line-height:1px;max-height:0px;max-width:0px;opacity:0;overflow:hidden;\"> Preview - Welcome to Coded Mails </div>\n" +
                "  <div style=\"background-color:#eaeaea;\">\n" +
                "    <!--[if mso | IE]>\n" +
                "      <table\n" +
                "         align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:600px;\" width=\"600\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "      <![endif]-->\n" +
                "    <div style=\"margin:0px auto;max-width:600px;\">\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center;\">\n" +
                "              <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "            <td\n" +
                "               class=\"\" style=\"vertical-align:top;width:600px;\"\n" +
                "            >\n" +
                "          <![endif]-->\n" +
                "              <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n" +
                "                  <tr>\n" +
                "                    <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "                      <!--[if mso | IE]>\n" +
                "    \n" +
                "        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td height=\"20\" style=\"vertical-align:top;height:20px;\">\n" +
                "      \n" +
                "    <![endif]-->\n" +
                "                      <div style=\"height:20px;\"> &nbsp; </div>\n" +
                "                      <!--[if mso | IE]>\n" +
                "    \n" +
                "        </td></tr></table>\n" +
                "      \n" +
                "    <![endif]-->\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </div>\n" +
                "              <!--[if mso | IE]>\n" +
                "            </td>\n" +
                "          \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "    <!--[if mso | IE]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      \n" +
                "      <table\n" +
                "         align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:600px;\" width=\"600\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "      \n" +
                "        <v:rect  style=\"width:600px;\" xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\">\n" +
                "        <v:fill  origin=\"0.5, 0\" position=\"0.5, 0\" src=\"https://images.unsplash.com/photo-1535551951406-a19828b0a76b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=80\" type=\"tile\" />\n" +
                "        <v:textbox style=\"mso-fit-shape-to-text:true\" inset=\"0,0,0,0\">\n" +
                "      <![endif]-->\n" +
                "    <div style=\"background:url(https://media.ambito.com/p/3935544951308328a4eb1711a74552fc/adjuntos/239/imagenes/039/514/0039514331/730x0/smart/tips-super-basicos-de-seguridad-informatica-5png.png) top center / cover no-repeat;margin:0px auto;border-radius:20px 20px 0 0;max-width:600px;\">\n" +
                "      <div style=\"line-height:0;font-size:0;\">\n" +
                "        <table align=\"center\" background=\"https://media.ambito.com/p/3935544951308328a4eb1711a74552fc/adjuntos/239/imagenes/039/514/0039514331/730x0/smart/tips-super-basicos-de-seguridad-informatica-5png.png\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:url(https://media.ambito.com/p/3935544951308328a4eb1711a74552fc/adjuntos/239/imagenes/039/514/0039514331/730x0/smart/tips-super-basicos-de-seguridad-informatica-5png.png) top center / cover no-repeat;width:100%;border-radius:20px 20px 0 0;\">\n" +
                "          <tbody>\n" +
                "            <tr>\n" +
                "              <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center;\">\n" +
                "                <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "            <td\n" +
                "               class=\"\" style=\"vertical-align:top;width:600px;\"\n" +
                "            >\n" +
                "          <![endif]-->\n" +
                "                <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" style=\"font-size:0px;padding:8px 0;word-break:break-word;\">\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td style=\"width:150px;\">\n" +
//                "                                <img height=\"auto\" src=\"../../../images/logo-white.png\" style=\"border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:16px;\" width=\"150\">\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "                        <!--[if mso | IE]>\n" +
                "    \n" +
                "        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td height=\"400\" style=\"vertical-align:top;height:400px;\">\n" +
                "      \n" +
                "    <![endif]-->\n" +
                "                        <div style=\"height:400px;\"> &nbsp; </div>\n" +
                "                        <!--[if mso | IE]>\n" +
                "    \n" +
                "        </td></tr></table>\n" +
                "      \n" +
                "    <![endif]-->\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </div>\n" +
                "                <!--[if mso | IE]>\n" +
                "            </td>\n" +
                "          \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <!--[if mso | IE]>\n" +
                "        </v:textbox>\n" +
                "      </v:rect>\n" +
                "    \n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      \n" +
                "      <table\n" +
                "         align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:600px;\" width=\"600\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "      <![endif]-->\n" +
                "    <div style=\"background:#ffffff;background-color:#ffffff;margin:0px auto;border-radius:0 0 20px 20px;max-width:600px;\">\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#ffffff;background-color:#ffffff;width:100%;border-radius:0 0 20px 20px;\">\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center;\">\n" +
                "              <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "            <td\n" +
                "               class=\"\" style=\"vertical-align:top;width:600px;\"\n" +
                "            >\n" +
                "          <![endif]-->\n" +
                "              <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n" +
                "                  <tr>\n" +
                "                    <td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;line-height:20px;text-align:left;color:#54595f;\">\n" +
                "                        <h1 style=\"margin: 0; font-size: 24px; line-height: 32px; line-height: normal; font-weight: bold;\"> Token de confirmacion</h1>\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;line-height:20px;text-align:left;color:#54595f;\">\n" +
                "                        <p style=\"margin: 5px 0;\">Hola "+  user + ", </p>\n" +
                "                        <p style=\"margin: 5px 0;\">Hemos recibido una petición de compra con su tarjeta de " + cardType + ". Para completar la transaccion debe ingresar el siguiente token:</p>\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td align=\"center\" vertical-align=\"middle\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:separate;line-height:100%;\">\n" +
                "                        <tr>\n" +
                "                          <td align=\"center\" bgcolor=\"#54595f\" role=\"presentation\" style=\"border:none;border-radius:30px;cursor:auto;mso-padding-alt:10px 25px;background:#54595f;\" valign=\"middle\">\n" +
                "                            <a style=\"display: inline-block; background: #54595f; color: white; font-family: Nunito, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: normal; line-height: 30px; margin: 0; text-decoration: none; text-transform: none; padding: 10px 25px; mso-padding-alt: 0px; border-radius: 30px;\" target=\"_blank\"> A-123456 </a>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </table>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;line-height:20px;text-align:left;color:#54595f;\">\n" +
                "                        <p style=\"margin: 5px 0;\">Si no solicitó realizar una nueva transaccion, infórmenos de inmediato respondiendo a este correo electrónico.. </p>\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;line-height:20px;text-align:left;color:#54595f;\">\n" +
                "                        <p style=\"margin: 5px 0;\">Gracias, <br> homebanking-grupo3</p>\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </div>\n" +
                "              <!--[if mso | IE]>\n" +
                "            </td>\n" +
                "          \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "    <!--[if mso | IE]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      \n" +
                "      <table\n" +
                "         align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:600px;\" width=\"600\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "      <![endif]-->\n" +
                "    <div style=\"margin:0px auto;max-width:600px;\">\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-bottom:0px;text-align:center;\">\n" +
                "              <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "            <td\n" +
                "               class=\"\" style=\"vertical-align:top;width:600px;\"\n" +
                "            >\n" +
                "          <![endif]-->\n" +
                "              <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n" +
                "                  <tr>\n" +
                "                    <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
//                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;line-height:20px;text-align:center;color:#54595f;\"><a href=\"#\" style=\"color: #54595f; display: inline-block; text-decoration: none;\">\n" +
//                "                          <img src=\"../../../images/google-play.png\" alt=\"play-store-logo\" width=\"150px\">\n" +
//                "                        </a>\n" +
//                "                        <a href=\"#\" style=\"color: #54595f; display: inline-block; text-decoration: none;\">\n" +
//                "                          <img src=\"../../../images/app-store.png\" alt=\"app-store-logo\" width=\"150px\">\n" +
//                "                        </a>\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </div>\n" +
                "              <!--[if mso | IE]>\n" +
                "            </td>\n" +
                "          \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "    <!--[if mso | IE]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      \n" +
                "      <table\n" +
                "         align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:600px;\" width=\"600\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "      <![endif]-->\n" +
                "    <div style=\"margin:0px auto;max-width:600px;\">\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-top:0px;text-align:center;\">\n" +
                "              <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "            <td\n" +
                "               class=\"\" style=\"vertical-align:top;width:600px;\"\n" +
                "            >\n" +
                "          <![endif]-->\n" +
                "              <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n" +
                "                  <tr>\n" +
                "                    <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:18px;text-align:center;color:#54595f;\">123 Homebanking street, Argentina.<br> © 2022 homebanking-grupo3 Inc.</div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\n" +
//                "                      <div style=\"font-family:Nunito, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:18px;text-align:center;color:#54595f;\">Update your <a href=\"https://google.com\" style=\"color: #54595f; text-decoration: underline;\">email preferences</a> to choose the types of emails you receive, or you can <a href=\"https://google.com\" style=\"color: #54595f; text-decoration: underline;\"> unsubscribe </a>from all future emails.</div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </div>\n" +
                "              <!--[if mso | IE]>\n" +
                "            </td>\n" +
                "          \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "    <!--[if mso | IE]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      \n" +
                "      <table\n" +
                "         align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:600px;\" width=\"600\"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">\n" +
                "      <![endif]-->\n" +
                "    <div style=\"margin:0px auto;max-width:600px;\">\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center;\">\n" +
                "              <!--[if mso | IE]>\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                \n" +
                "        <tr>\n" +
                "      \n" +
                "            <td\n" +
                "               class=\"\" style=\"vertical-align:top;width:600px;\"\n" +
                "            >\n" +
                "          <![endif]-->\n" +
                "              <div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n" +
                "                  <tr>\n" +
                "                    <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "                      <!--[if mso | IE]>\n" +
                "    \n" +
                "        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td height=\"1\" style=\"vertical-align:top;height:1px;\">\n" +
                "      \n" +
                "    <![endif]-->\n" +
                "                      <div style=\"height:1px;\"> &nbsp; </div>\n" +
                "                      <!--[if mso | IE]>\n" +
                "    \n" +
                "        </td></tr></table>\n" +
                "      \n" +
                "    <![endif]-->\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </div>\n" +
                "              <!--[if mso | IE]>\n" +
                "            </td>\n" +
                "          \n" +
                "        </tr>\n" +
                "      \n" +
                "                  </table>\n" +
                "                <![endif]-->\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "    <!--[if mso | IE]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      <![endif]-->\n" +
                "  </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(toEmail);
        helper.setSubject(subject);
//        helper.setFrom("testmailequipo03@gmail.com");
        mailSender.send(mimeMessage);

/*        message.setFrom("homebanking5574684@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);*/

        System.out.println("El mail fue enviado correctamente");
    }
}
