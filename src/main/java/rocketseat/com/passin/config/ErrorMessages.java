package rocketseat.com.passin.config;

public class ErrorMessages {
  public static final String INVALID_SIGNUP_DATA = "O usuário deve informar: Nome, E-mail, Senha, CPF e Data de nascimento e informações de endereço, tais como: País, UF, cidade, rua, CEP e, opcionalmente, bairro e complemento.";
  public static final String INVALID_EMAIL = "E-mail inválido!";
  public static final String INVALID_CPF = "CPF inválido!";
  public static final String INVALID_PASSWORD = "A senha deve ser composta por no mínimo 8 caractéres, conter ao menos uma letra maiúscula e ao menos um número";
  public static final String INVALID_ZIPCODE = "CEP inválido!";
  public static final String EMAIL_ALREADY_IN_USE = "E-mail já registrado!";
  public static final String CPF_ALREADY_IN_USE = "CPF já registrado!";
  public static final String USER_NOT_FOUND = "Usuário não encontrado!";
  public static final String ACCESS_TOKEN_NOT_FOUND = "Token de acesso não encontrado";
  public static final String SEND_CONFIRMATION_MAIL_ERROR = "Não foi possível enviar o e-mail de confirmação!";
  public static final String USER_NOT_CONFIRMED = "O e-mail deste usuário ainda não foi confirmado. Cheque sua caixa de entrada!";
  public static final String INVALID_PIN_CODE = "Código de confirmação inválido!";
  public static final String SIGNUP_ERROR = "Não foi possível registrar o usuário!";
  public static final String NO_ROLES_ERROR = "Este usuário não possui papéis atribuídos!";
  public static final String USER_IS_NOT_EVENT_OWNER = "Este usuário não é um gerente de eventos!";
  public static final String INVALID_EVENT_CREATION_DATA = "O usuário deve informar: Nome, detalhes, número máximo de participantes, data de início, data de fim e endereço de forma opcional. Se informado o endereço deve conter: País, UF, cidade, rua, CEP e, opcionalmente, bairro e complemento.";
  public static final String INVALID_SIGN_IN_DATA = "Login inválido!";
  public static final String ACCOUNT_ALREADY_CONFIRMED = "Esta conta já foi confirmada!";
  public static final String ACCESS_DENIED = "Você não possui permissão para executar esta ação!";
  public static final String UNAUTHORIZED = "Você não possui autorização para acessar este recurso!";
}
