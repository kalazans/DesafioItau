# DesafioItau
<h1>Resolvendo o desafio itau!! info no link <a href="https://github.com/feltex/desafio-itau-backend">desafio</a></h1>
<p>Esta documentado com swagger, só baixar e configurar e rodar o projeto e abrir o swagger</p>
<ul>Confiraçoes que tem q ser feitar:
<li>configurar o acesso ao banco de dados e o driver de acesso no application properties</li>
<p>Todas os requisiçoes do desafio foram feitas, a nao ser usar docker e Observabilidade</p>
<p>A aplicaçao tem testes automatizados,Loggings do que acontece na aplicaçao e tratamento de erros</p>
<p>A Configuraçao de segundos, eu deixei pela a requisiçao com @PathVariable -> se for vazia, roda nos 60s ou se vier com o tempo EM SEGUNDOS, faz a busca por ele</p>
<p>A unica coisa que nao deu pra resolver foi o Json receber fields a mais!!! TENTEI spring.jackson.deserialization.fail-on-unknown-properties=true, tentei instanciar com o Bean um novo jackson, mas nao funcionou de jeito algum<br>
quem souber uma resoluçao, ajuda ae por favor</p>
<p>Foto do swagger abaixo:</p>
[swag](https://github.com/user-attachments/assets/413b3803-a2fd-4827-8ed8-9c724023f446)
<br>
<p>Quem souber resolver o problema do Jackson, um grande obrigado</p>
